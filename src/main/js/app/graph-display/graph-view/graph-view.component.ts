import { Component, Input } from '@angular/core';
import { Graph } from 'app/models/graph';

@Component({
  selector: 'graph-view',
  templateUrl: './graph-view.component.html',
  styleUrls: ['./graph-view.component.sass']
})
export class GraphViewComponent {

  @Input() graph: Graph;

  @Input() nodeName: string;

  constructor() { }

  onSelectNode(newItem: string) {
    this.nodeName = newItem;
  }

}
