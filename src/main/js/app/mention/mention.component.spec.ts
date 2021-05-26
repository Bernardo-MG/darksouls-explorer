import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { ApolloTestingModule } from 'apollo-angular/testing';
import { Observable, of } from 'rxjs';

import { MentionService } from '../mention.service';
import { MentionComponent } from './mention.component';
import { Relationship } from '../relationship';

class MockedMentionService {

  getMentions(): Observable<Relationship[]> {
    return of([]);
  }

}

describe('MentionComponent', () => {
  let component: MentionComponent;
  let fixture: ComponentFixture<MentionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MentionComponent ],
      imports: [RouterTestingModule, ApolloTestingModule],
      providers: [
        { provides: MentionService, useClass: MockedMentionService }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MentionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
