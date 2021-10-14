import { RouterTestingModule } from '@angular/router/testing';
import { EmployeeService } from './../employee.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateEmployeeComponent } from './update-employee.component';

describe('UpdateEmployeeComponent', () => {
  let component: UpdateEmployeeComponent;
  let fixture: ComponentFixture<UpdateEmployeeComponent>;

  const fakeActivatedRoute = {
        snapshot: { data: {  } }
      } as ActivatedRoute;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        imports : [RouterTestingModule, HttpClientTestingModule],
        providers: [{provide: ActivatedRoute, useValue: fakeActivatedRoute}, EmployeeService ],
        declarations: [ UpdateEmployeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check Submit button triggering onSubmit method', async(() => {
        spyOn(component, 'onSubmit');

        let button = fixture.debugElement.nativeElement.querySelector('.btn');
        button.click();

        fixture.whenStable().then(() => {
        expect(component.onSubmit).toHaveBeenCalled();
        });
    }));

});
