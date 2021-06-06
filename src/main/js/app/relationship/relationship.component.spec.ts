import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { ApolloTestingModule } from 'apollo-angular/testing';
import { Observable, of } from 'rxjs';

import { RelationshipService } from 'app/services/relationship.service';
import { RelationshipComponent } from './relationship.component';
import { Relationship } from 'app/models/relationship';

class MockedRelationshipService {

  getRelationships(): Observable<Relationship[]> {
    return of([]);
  }

}

describe('RelationshipComponent', () => {
  let component: RelationshipComponent;
  let fixture: ComponentFixture<RelationshipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RelationshipComponent ],
      imports: [RouterTestingModule, ApolloTestingModule],
      providers: [
        { provides: RelationshipService, useClass: MockedRelationshipService }
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
