import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { ApolloTestingModule } from 'apollo-angular/testing';
import { Observable, of } from 'rxjs';

import { GraphService } from 'app/services/graph.service';
import { RelationshipComponent } from './relationship.component';
import { Graph } from 'app/models/graph';

class MockedGraphService {

  getGraph(relationship: String): Observable<Graph> {
    return of({ nodes: [], links: [], types: [] });
  }

}

describe('GraphComponent', () => {
  let component: RelationshipComponent;
  let fixture: ComponentFixture<RelationshipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RelationshipComponent],
      imports: [RouterTestingModule, ApolloTestingModule],
      providers: [
        { provides: GraphService, useClass: MockedGraphService }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RelationshipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
