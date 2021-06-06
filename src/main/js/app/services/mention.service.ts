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
            relationships(type: "MENTIONS") {
              source,
              sourceId,
              target,
              targetId,
              type
            }
          }
        `,
      })
      .valueChanges.pipe(map((response: ApolloQueryResult<MentionResponse>) => { return response.data.relationships }));

    return mentions.pipe(map(this.toGraph));
  }

  toGraph(relationships: Relationship[]): Graph {
    const targets = relationships.map((relationship: Relationship) => { return { id: relationship.targetId, name: relationship.target } });
    const sources = relationships.map((relationship: Relationship) => { return { id: relationship.sourceId, name: relationship.source } });
    const allTypes = relationships.map((relationship: Relationship) => { return relationship.type });

    const nodeIds = new Set();
    const removeDuplicatedNodes = (data: Node[], item: Node) => {
      let result;

      if (nodeIds.has(item.id)) {
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

      if (typeIds.has(item)) {
        result = data;
      } else {
        typeIds.add(item);
        result = [...data, item];
      }

      return result;
    }

    const nodes = targets.concat(sources).reduce(removeDuplicatedNodes, []);
    const links = relationships.map((relationship: Relationship) => { return { source: relationship.sourceId, target: relationship.targetId, type: relationship.type } });
    const types = allTypes.reduce(removeDuplicatedTypes, []);

    return { nodes, links, types }
  }


}
