import { Component, Input } from '@angular/core';
import { GraphService } from 'app/services/graph.service';
import { Graph } from 'app/models/graph';
import { NamedValue } from 'app/models/namedValue';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.sass']
})
export class RelationshipComponent {

  filterOptions: NamedValue[] = [{ name: 'Mentions', value: 'MENTIONS' }, { name: 'From', value: 'FROM' }];

  graph: Graph = { nodes: [], links: [], types: [] };

  @Input() nodeName: string;

  constructor(
    private graphService: GraphService
  ) { }

  runQuery(options: String[]) {
    if (options.length > 0) {
      this.graphService.getGraph(options).subscribe(data => this.graph = data);
    }
  }

  onSelectNode(newItem: string) {
    this.nodeName = newItem;
  }

  onApplyFilter(options: String[]) {
    this.runQuery(options);
  }

}
