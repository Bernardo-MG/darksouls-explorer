import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MentionComponent } from './mention/mention.component';
import { GraphDisplayComponent } from './graph-display/graph-display.component';

const routes: Routes = [
  { path: '', redirectTo: '/graph', pathMatch: 'full' },
  { path: 'mentions', component: MentionComponent },
  { path: 'graph', component: GraphDisplayComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
