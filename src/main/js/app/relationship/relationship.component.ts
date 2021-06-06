import { Component, OnInit } from '@angular/core';
import { RelationshipService } from 'app/services/relationship.service';
import { Graph } from 'app/models/graph';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.sass']
})
export class RelationshipComponent implements OnInit {

  graph: Graph = { nodes: [], links: [], types: [] };

  constructor(
    private relationshipService: RelationshipService
  ) { }

  ngOnInit(): void {
    this.relationshipService.getRelationships().subscribe(data => this.graph = data);
  }

}
