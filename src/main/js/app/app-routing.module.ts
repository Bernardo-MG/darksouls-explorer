import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RelationshipComponent } from './relationship/relationship.component';

const routes: Routes = [
  { path: '', redirectTo: '/relationships', pathMatch: 'full' },
  { path: 'relationships', component: RelationshipComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
