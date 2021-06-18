import { Component, OnInit } from '@angular/core';
import { GraphService } from 'app/services/graph.service';
import { Graph } from 'app/models/graph';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.sass']
})
export class RelationshipComponent implements OnInit {

  graph: Graph = { nodes: [], links: [], types: [] };

  constructor(
    private graphService: GraphService
  ) { }

  ngOnInit(): void {
    this.graphService.getGraph('MENTIONS').subscribe(data => this.graph = data);
  }

}
