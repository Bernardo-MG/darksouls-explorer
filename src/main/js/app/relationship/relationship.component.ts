import { Component, Input, OnInit } from '@angular/core';
import { GraphService } from 'app/services/graph.service';
import { Graph } from 'app/models/graph';
import { NamedValue } from 'app/models/namedValue';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.sass']
})
export class RelationshipComponent implements OnInit {

  filterOptions: NamedValue[] = [];

  graph: Graph = { nodes: [], links: [], types: [] };

  @Input() nodeName: string;

  constructor(
    private graphService: GraphService
  ) { }

  ngOnInit(): void {
    this.graphService.getAllTypes().subscribe(types => this.filterOptions = types.map((type) => { return { name: type, value: type } }));
  }

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
