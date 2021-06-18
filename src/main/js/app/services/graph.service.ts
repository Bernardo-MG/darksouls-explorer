import { Injectable } from '@angular/core';
import { Observable, of, Subscription } from 'rxjs';
import { Apollo, gql } from 'apollo-angular';
import { map } from 'rxjs/operators';
import { GraphResponse } from '../models/graphResponse';
import { Graph } from '../models/graph';
import { ApolloQueryResult } from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class GraphService {

  constructor(
    private apollo: Apollo
  ) { }

  getGraph(relationship: String): Observable<Graph> {
    const graph = this.apollo
      .watchQuery({
        query: gql`
          {
            graph(type: "${relationship}") {
              nodes {
                id,
                name
              },
              links {
                source,
                sourceId,
                target,
                targetId,
                type
              },
              types
            }
          }
        `,
      })
      .valueChanges.pipe(map((response: ApolloQueryResult<GraphResponse>) => { return response.data.graph }));

    return graph;
  }

}
