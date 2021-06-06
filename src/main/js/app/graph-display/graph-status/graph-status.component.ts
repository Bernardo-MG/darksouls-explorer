import { Component, Input } from '@angular/core';
import { Graph } from 'app/graph';

@Component({
  selector: 'graph-status',
  templateUrl: './graph-status.component.html',
  styleUrls: ['./graph-status.component.sass']
})
export class GraphStatusComponent {

  @Input() graph: Graph;

  constructor() { }

}
