import { Component, OnInit } from '@angular/core';
import { MentionService } from '../mention.service';
import { Graph } from 'app/graph';

@Component({
  selector: 'app-mention',
  templateUrl: './mention.component.html',
  styleUrls: ['./mention.component.sass']
})
export class MentionComponent implements OnInit {

  graph: Graph = { nodes: [], links: [] };

  constructor(
    private mentionService: MentionService
  ) { }

  ngOnInit(): void {
    this.mentionService.getMentions().subscribe(data => this.graph = data);
  }

}
