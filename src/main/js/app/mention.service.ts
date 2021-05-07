import { Injectable } from '@angular/core';
import { Observable, of, Subscription } from 'rxjs';
import { Apollo, gql } from 'apollo-angular';
import { map } from 'rxjs/operators';
import { Mention } from './mention';
import { Graph } from './graph';
import { Node } from './node';
import { MentionResponse } from './mentionResponse';
import { ApolloQueryResult } from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class MentionService {

  constructor(
    private apollo: Apollo
  ) { }

  getMentions(): Observable<Graph> {
    const mentions = this.apollo
      .watchQuery({
        query: gql`
          {
            allMentions {
              mentioned,
              mentionedId,
              mentioner,
              mentionerId,
              mention
            }
          }
        `,
      })
      .valueChanges.pipe(map((response: ApolloQueryResult<MentionResponse>) => { return response.data.allMentions }));

    return mentions.pipe(map(this.toGraph));
  }

  toGraph(mentions: Mention[]): Graph {
    const mentioned = mentions.map((mention: Mention) => { return { id: mention.mentionedId, name: mention.mentioned } });
    const mentioners = mentions.map((mention: Mention) => { return { id: mention.mentionerId, name: mention.mentioner } });

    const ids = new Set();
    const removeDuplicates = (data: Node[], item: Node) => {
      let result;

      if(ids.has(item.id)){
        result = data;
      } else {
        ids.add(item.id);
        result = [...data, item];
      }

      return result;
    }
    const nodes = mentioned.concat(mentioners).reduce(removeDuplicates, []);

    const links = mentions.map((mention: Mention) => { return { source: mention.mentionerId, target: mention.mentionedId } });

    return { nodes, links }
  }
  

}
