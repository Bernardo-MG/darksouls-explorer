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

  getGraph(relationships: String[]): Observable<Graph> {
    const relArg = relationships.map((v) => '"' + v + '"').join(',');
    return this.apollo
      .watchQuery({
        query: gql`
          {
            graph(type: [${relArg}]) {
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
  }

  getAllTypes(): Observable<String[]> {
    return this.apollo
      .watchQuery({
        query: gql`
          {
            graph {
              types
            }
          }
        `,
      })
      .valueChanges.pipe(map((response: ApolloQueryResult<GraphResponse>) => { return response.data.graph.types }));
  }

}
