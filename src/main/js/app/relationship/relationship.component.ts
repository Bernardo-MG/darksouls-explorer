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

  runQuery(options) {
    const rels: String[] = options.filter(value => value.selected).map(rel => rel.value);
    if(rels.length > 0){
      this.graphService.getGraph(rels).subscribe(data => this.graph = data);
    }
  }

  onSelectNode(newItem: string) {
    this.nodeName = newItem;
  }

  onApplyFilter(options) {
    this.runQuery(options);
  }

}
