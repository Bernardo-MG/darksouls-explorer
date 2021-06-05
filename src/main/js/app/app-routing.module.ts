import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MentionComponent } from './mention/mention.component';

const routes: Routes = [
  { path: '', redirectTo: '/mentions', pathMatch: 'full' },
  { path: 'mentions', component: MentionComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
