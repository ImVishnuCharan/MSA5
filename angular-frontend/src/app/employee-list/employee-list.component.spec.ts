import { RouterTestingModule } from '@angular/router/testing';
import { EmployeeService } from './../employee.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeListComponent } from './employee-list.component';


describe('EmployeeListComponent', () => {
  let component: EmployeeListComponent;
  let fixture: ComponentFixture<EmployeeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        imports : [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
        providers : [EmployeeService],
      declarations: [ EmployeeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check Update button triggering updateEmployee method', async(() => {
        spyOn(component, 'updateEmployee');

        let button = fixture.debugElement.nativeElement.querySelector('#update');
        button.click();

        fixture.whenStable().then(() => {
        expect(component.updateEmployee).toHaveBeenCalled();
        });
    }));

  it('should check Delete button triggering deleteEmployee method', async(() => {
        spyOn(component, 'deleteEmployee');

        let button = fixture.debugElement.nativeElement.querySelector('#delete');
        button.click();

        fixture.whenStable().then(() => {
        expect(component.deleteEmployee).toHaveBeenCalled();
        });
    }));
    
  it('should check Details button triggering employeeDetails method', async(() => {
        spyOn(component, 'employeeDetails');

        let button = fixture.debugElement.nativeElement.querySelector('#details');
        button.click();

        fixture.whenStable().then(() => {
        expect(component.employeeDetails).toHaveBeenCalled();
        });
    }));

});
