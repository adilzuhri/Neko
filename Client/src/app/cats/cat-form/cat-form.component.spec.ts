import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatFormComponent } from './cat-form.component';

describe('CatFormComponent', () => {
  let component: CatFormComponent;
  let fixture: ComponentFixture<CatFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CatFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
