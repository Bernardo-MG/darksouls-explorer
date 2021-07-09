import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GraphStatusComponent } from './graph-status/graph-status.component';
import { GraphDiagramComponent } from './graph-diagram/graph-diagram.component';

@NgModule({
  declarations: [
    GraphStatusComponent,
    GraphDiagramComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    CommonModule,
    GraphStatusComponent,
    GraphDiagramComponent
  ]
})
export class GraphDisplayModule { }
