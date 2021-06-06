import { Component, Input, OnChanges, OnInit, ViewEncapsulation } from '@angular/core';
import { Graph } from 'app/models/graph';
import * as d3 from 'd3';

@Component({
  selector: 'graph-diagram',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './graph-diagram.component.html',
  styleUrls: ['./graph-diagram.component.sass']
})
export class GraphDiagramComponent implements OnInit, OnChanges {

  @Input() graph: Graph;

  constructor() { }

  ngOnInit(): void {
    this.cleanGraph();
    if (this.graph) {
      this.displayGraph(this.graph);
    }
  }

  ngOnChanges(): void {
    this.cleanGraph();
    if (this.graph) {
      this.displayGraph(this.graph);
    }
  }

  private cleanGraph() {
    d3.select("figure#graph_view").select("svg").remove();
  }

  private displayGraph(data: Graph) {
    const links: any[] = data.links;
    const nodes = data.nodes;
    const types: String[] = data.types;

    const width = 800;
    const height = 600;

    const color = d3.scaleOrdinal(types, d3.schemeCategory10)

    // Graph simulation
    const simulation = d3.forceSimulation(nodes)
      .force("link", d3.forceLink(links).id((d: any) => d.id))
      .force("charge", d3.forceManyBody())
      .force("center", d3.forceCenter(width / 2, height / 2));

    // Main view container
    var mainView = d3.select("figure#graph_view")
      .append("svg")
      .attr("id", "graph")
      .attr("viewBox", [0, 0, width, height] as any);

    // Per-type markers, as they don't inherit styles.
    mainView.append("defs").selectAll("marker")
      .data(types)
      .join("marker")
      .attr("id", d => `arrow-${d}`)
      .attr("viewBox", "0 -5 10 10")
      .attr("refX", 15)
      .attr("refY", -0.5)
      .attr("markerWidth", 6)
      .attr("markerHeight", 6)
      .attr("orient", "auto")
      .append("path")
      .attr("fill", color)
      .attr("d", "M0,-5L10,0L0,5");

    // Builds links
    const link = mainView.append("g")
      .attr("fill", "none")
      .attr("stroke-width", 1.5)
      .selectAll("path")
      .data(links)
      .join("path")
      .attr("class", "graph_link")
      .attr("stroke", d => color(d.type))
      .attr("marker-end", d => `url(#arrow-${d.type})`);

    // Builds nodes
    // Added after the links so they are drawn over them
    const nodeRoot = mainView.append("g")
      .selectAll("g")
      .data(nodes)
      .join("g")
      .call(this.drag(simulation) as any);
    const node = nodeRoot.append("circle")
      .attr("class", "graph_node")
      .on("mouseover", this.mouseoverButton)
      .on("mouseout", this.mouseoutButton);
    const nodeLabel = nodeRoot.append("text")
      .text(d => d.name as string);

    // Adds zoom
    mainView.call(d3.zoom()
      .extent([[0, 0], [width, height]])
      .scaleExtent([0.5, 5])
      .on("zoom", (event) => {
        link.attr('transform', event.transform);
        nodeRoot.attr('transform', event.transform);
      })
    );

    // Runs simulation
    simulation.on("tick", () => {
      link.attr("d", this.linkArc);

      node
        .attr("cx", d => d.x)
        .attr("cy", d => d.y);

      nodeLabel
        .attr("x", d => d.x)
        .attr("y", d => d.y);
    });
  }

  private linkArc(d) {
    const r = Math.hypot(d.target.x - d.source.x, d.target.y - d.source.y);
    return `
      M${d.source.x},${d.source.y}
      A${r},${r} 0 0,1 ${d.target.x},${d.target.y}
    `;
  }

  private drag(simulation) {
    function dragstarted(event) {
      if (!event.active) simulation.alphaTarget(0.3).restart();
      event.subject.fx = event.subject.x;
      event.subject.fy = event.subject.y;
    }

    function dragged(event) {
      event.subject.fx = event.x;
      event.subject.fy = event.y;
    }

    function dragended(event) {
      if (!event.active) simulation.alphaTarget(0);
      event.subject.fx = null;
      event.subject.fy = null;
    }

    return d3.drag()
      .on("start", dragstarted)
      .on("drag", dragged)
      .on("end", dragended)
  }

  private mouseoverButton(event, i) {
    d3.select(event.target)
      .style("cursor", "pointer")
      .classed("graph_node_selected", true);
  }

  private mouseoutButton(event, i) {
    d3.select(event.target)
      .style("cursor", "default")
      .classed("graph_node_selected", false);
  }

}
