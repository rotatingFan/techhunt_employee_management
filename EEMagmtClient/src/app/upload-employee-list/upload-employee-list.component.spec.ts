import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadEmployeeListComponent } from './upload-employee-list.component';

describe('UploadEmployeeListComponent', () => {
  let component: UploadEmployeeListComponent;
  let fixture: ComponentFixture<UploadEmployeeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadEmployeeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadEmployeeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
