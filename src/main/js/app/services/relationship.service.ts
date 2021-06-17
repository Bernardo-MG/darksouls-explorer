import { Injectable } from '@angular/core';
import { Observable, of, Subscription } from 'rxjs';
import { Apollo, gql } from 'apollo-angular';
import { map } from 'rxjs/operators';
import { Relationship } from '../models/relationship';
import { Graph } from '../models/graph';
import { Node } from '../models/node';
import { RelationshipResponse } from '../models/relationshipResponse';
import { ApolloQueryResult } from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(
    private apollo: Apollo
  ) { }

  getRelationships(): Observable<Graph> {
    const relationships = this.apollo
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
      .valueChanges.pipe(map((response: ApolloQueryResult<RelationshipResponse>) => { return response.data.relationships }));

    return relationships.pipe(map((v) => this.toGraph(v, this.removeDuplicates)));
  }

  removeDuplicates<T>(data: T[], mapper: Function): T[] {
    const ids = new Set();
    const removeDuplicated = (data: T[], item: T) => {
      let result: T[];

      if (ids.has(mapper(item))) {
        result = data;
      } else {
        ids.add(mapper(item));
        result = [...data, item];
      }

      return result;
    }

    return data.reduce(removeDuplicated, []);
  }

  toGraph(relationships: Relationship[], removeDuplicates: Function): Graph {
    // Which nodes the relationships point from
    const sources = relationships.map((relationship: Relationship) => { return { id: relationship.sourceId, name: relationship.source } });
    // Which nodes the relationships point to
    const targets = relationships.map((relationship: Relationship) => { return { id: relationship.targetId, name: relationship.target } });
    // All the relationship types
    const allTypes = relationships.map((relationship: Relationship) => { return relationship.type });

    const nodes = removeDuplicates(targets.concat(sources), (value: Node) => value.id);
    const links = relationships.map((relationship: Relationship) => { return { source: relationship.sourceId, target: relationship.targetId, type: relationship.type } });
    const types = removeDuplicates(allTypes, (value: String) => value);

    return { nodes, links, types }
  }


}
