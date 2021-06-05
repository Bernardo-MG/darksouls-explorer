import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraphStatusComponent } from './graph-status.component';

describe('GraphStatusComponent', () => {
  let component: GraphStatusComponent;
  let fixture: ComponentFixture<GraphStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GraphStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GraphStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
