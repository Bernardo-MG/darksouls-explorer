import { Component, Input } from '@angular/core';
import { Graph } from 'app/models/graph';
import * as d3 from 'd3';

@Component({
  selector: 'graph-view',
  templateUrl: './graph-view.component.html',
  styleUrls: ['./graph-view.component.sass']
})
export class GraphViewComponent {

  @Input() graph: Graph;

  constructor() { }

}
