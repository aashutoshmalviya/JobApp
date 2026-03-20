import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewJobs } from './view-jobs';

describe('ViewJobs', () => {
  let component: ViewJobs;
  let fixture: ComponentFixture<ViewJobs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewJobs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewJobs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
