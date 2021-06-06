import { Component, OnInit } from '@angular/core';
import { MentionService } from 'app/mention.service';
import { Graph } from 'app/models/graph';

@Component({
  selector: 'app-mention',
  templateUrl: './mention.component.html',
  styleUrls: ['./mention.component.sass']
})
export class MentionComponent implements OnInit {

  graph: Graph = { nodes: [], links: [], types: [] };

  constructor(
    private mentionService: MentionService
  ) { }

  ngOnInit(): void {
    this.mentionService.getMentions().subscribe(data => this.graph = data);
  }

}
