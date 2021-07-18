import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { Graph } from 'app/models/graph';
import { GraphLink } from '../models/graphLink';
import { GraphNode } from '../models/graphNode';
import * as d3 from 'd3';
import { Link } from 'app/models/link';
import { Node } from 'app/models/node';

@Component({
  selector: 'graph-diagram',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './graph-diagram.component.html',
  styleUrls: ['./graph-diagram.component.sass']
})
export class GraphDiagramComponent implements OnInit, OnChanges {

  @Input() graph: Graph;

  @Output() selectNode = new EventEmitter<Number>();

  height = 300;

  width = 400;

  constructor() { }

  ngOnInit(): void {
    this.cleanGraph();
    if (this.graph) {
      const links = this.graph.links.map(this.toGraphLink);
      const nodes = this.graph.nodes.map(this.toGraphNode);
      const types = this.graph.types;

      // const rect: DOMRect = (d3.select("figure#graph_view").node() as Element).getBoundingClientRect();
      // this.height = rect.height
      // this.width = rect.width

      this.displayGraph(links, nodes, types, this.width, this.height);
    }
  }

  ngOnChanges(): void {
    this.cleanGraph();
    if (this.graph) {
      const links = this.graph.links.map(this.toGraphLink);
      const nodes = this.graph.nodes.map(this.toGraphNode);
      const types = this.graph.types;

      this.displayGraph(links, nodes, types, this.width, this.height);
    }
  }

  private toGraphLink(data: Link): GraphLink {
    return { source: data.sourceId, target: data.targetId, type: data.type };
  }

  private toGraphNode(data: Node): GraphNode {
    return { id: data.id, name: data.name, x: 0, y: 0 };
  }

  private cleanGraph() {
    d3.select("figure#graph_view").select("#graph").remove();
  }

  private displayGraph(links: GraphLink[], nodes: GraphNode[], types: String[], width: number, height: number) {
    const color = d3.scaleOrdinal(types, d3.schemeCategory10)

    // Graph simulation
    const simulation = d3.forceSimulation(nodes)
      .force("link", d3.forceLink(links).id((d: any) => d.id))
      .force("charge", d3.forceManyBody())
      .force("center", d3.forceRadial(width / 2, height / 2));

    // Main view container
    var mainView = d3.select("figure#graph_view")
      .append("div")
      .attr("id", "graph")
      .classed("svg-container", true)
      .append("svg")
      .attr("viewBox", `0, 0, ${width}, ${height}`)
      .classed("svg-content-responsive", true);

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
      .on("mouseout", this.mouseoutButton)
      .on("click", (event, item) => this.selectNode.emit(item.id));
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
