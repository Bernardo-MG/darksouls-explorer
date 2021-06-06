import { Injectable } from '@angular/core';
import { Observable, of, Subscription } from 'rxjs';
import { Apollo, gql } from 'apollo-angular';
import { map } from 'rxjs/operators';
import { Relationship } from '../models/relationship';
import { Graph } from '../models/graph';
import { Node } from '../models/node';
import { MentionResponse } from '../models/mentionResponse';
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
              source,
              sourceId,
              target,
              targetId,
              type
            }
          }
        `,
      })
      .valueChanges.pipe(map((response: ApolloQueryResult<MentionResponse>) => { return response.data.allMentions }));

    return mentions.pipe(map(this.toGraph));
  }

  toGraph(mentions: Relationship[]): Graph {
    const mentioned = mentions.map((mention: Relationship) => { return { id: mention.targetId, name: mention.target } });
    const mentioners = mentions.map((mention: Relationship) => { return { id: mention.sourceId, name: mention.source } });
    const allTypes = mentions.map((mention: Relationship) => { return mention.type});

    const nodeIds = new Set();
    const removeDuplicatedNodes = (data: Node[], item: Node) => {
      let result;

      if(nodeIds.has(item.id)){
        result = data;
      } else {
        nodeIds.add(item.id);
        result = [...data, item];
      }

      return result;
    }
    const typeIds = new Set();
    const removeDuplicatedTypes = (data: any[], item: any) => {
      let result;

      if(typeIds.has(item)){
        result = data;
      } else {
        typeIds.add(item);
        result = [...data, item];
      }

      return result;
    }

    const nodes = mentioned.concat(mentioners).reduce(removeDuplicatedNodes, []);
    const links = mentions.map((mention: Relationship) => { return { source: mention.sourceId, target: mention.targetId, type: mention.type } });
    const types = allTypes.reduce(removeDuplicatedTypes, []);

    return { nodes, links, types }
  }
  

}
