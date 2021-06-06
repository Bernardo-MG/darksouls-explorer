import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraphDiagramComponent } from './graph-diagram.component';

describe('GraphDiagramComponent', () => {
  let component: GraphDiagramComponent;
  let fixture: ComponentFixture<GraphDiagramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GraphDiagramComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GraphDiagramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
