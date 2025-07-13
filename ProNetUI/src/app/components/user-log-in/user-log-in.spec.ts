import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLogIn } from './user-log-in';

describe('UserLogIn', () => {
  let component: UserLogIn;
  let fixture: ComponentFixture<UserLogIn>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserLogIn]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserLogIn);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
