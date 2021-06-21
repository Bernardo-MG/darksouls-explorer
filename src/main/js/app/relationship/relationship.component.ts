import { Component, Input } from '@angular/core';
import { GraphService } from 'app/services/graph.service';
import { Graph } from 'app/models/graph';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.sass']
})
export class RelationshipComponent {

  graph: Graph = { nodes: [], links: [], types: [] };

  @Input() nodeName: string;

  constructor(
    private graphService: GraphService
  ) { }

  onSelectRelationship() {
    this.graphService.getGraph('MENTIONS').subscribe(data => this.graph = data);
  }

  onSelectNode(newItem: string) {
    this.nodeName = newItem;
  }

}
