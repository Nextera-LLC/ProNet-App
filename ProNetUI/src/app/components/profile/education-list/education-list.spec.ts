import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EducationList } from './education-list';

describe('EducationList', () => {
  let component: EducationList;
  let fixture: ComponentFixture<EducationList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EducationList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EducationList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
