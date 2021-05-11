import { Component, OnInit, OnChanges, Input, ViewEncapsulation } from '@angular/core';
import { Graph } from 'app/graph';
import * as d3 from 'd3';

@Component({
  selector: 'app-graph-display',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './graph-display.component.html',
  styleUrls: ['./graph-display.component.sass']
})
export class GraphDisplayComponent implements OnInit, OnChanges {

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

    var width = 800;
    var height = 600;

    const simulation = d3.forceSimulation(nodes)
      .force("link", d3.forceLink(links).id((d: any) => d.id))
      .force("charge", d3.forceManyBody())
      .force("center", d3.forceCenter(width / 2, height / 2));

    var mainView = d3.select("figure#graph_view")
      .append("svg")
      .attr("id", "graph")
      .attr("viewBox", [0, 0, width, height] as any);

    const link = mainView.append("g")
      .selectAll("line")
      .data(links)
      .join("line")
      .attr("class", "graph_link");

    const node = mainView.append("g")
      .selectAll("circle")
      .data(nodes)
      .join("circle")
      .attr("class", "graph_node")
      .call(this.drag(simulation) as any);

    mainView.call(d3.zoom()
      .extent([[0, 0], [width, height]])
      .scaleExtent([0.5, 5])
      .on("zoom", (event) => {
        link.attr('transform', event.transform);
        node.attr('transform', event.transform);
      })
    );

    node.append("title")
      .text(d => d.name as string);

    simulation.on("tick", () => {
      link
        .attr("x1", d => d.source.x)
        .attr("y1", d => d.source.y)
        .attr("x2", d => d.target.x)
        .attr("y2", d => d.target.y);

      node
        .attr("cx", d => d.x)
        .attr("cy", d => d.y);
    });
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

}
