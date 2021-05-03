import { Component, OnInit } from '@angular/core';
import { MentionService } from '../mention.service';
import { Mention } from '../mention';

@Component({
  selector: 'app-mention',
  templateUrl: './mention.component.html',
  styleUrls: ['./mention.component.sass']
})
export class MentionComponent implements OnInit {

  columnsToDisplay = ['mentioned', 'mentioner', 'mention'];

  mentions: Mention[];

  constructor(
    private mentionService: MentionService
  ) { }

  ngOnInit(): void {
    this.mentionService.getMentions().subscribe(data => this.mentions = data);
  }

}
