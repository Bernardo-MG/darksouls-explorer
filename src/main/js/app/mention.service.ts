import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Apollo, gql } from 'apollo-angular';
import { map } from 'rxjs/operators';
import { Mention } from './mention';
import { MentionResponse } from './mentionResponse';
import { ApolloQueryResult } from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class MentionService {

  constructor(
    private apollo: Apollo
  ) { }

  getMentions(): Observable<Mention[]> {
    return this.apollo
      .watchQuery({
        query: gql`
          {
            allMentions {
              name,
              source,
              mention
            }
          }
        `,
      })
      .valueChanges.pipe(map((response: ApolloQueryResult<MentionResponse>) => { return response.data.allMentions }));
  }

}
