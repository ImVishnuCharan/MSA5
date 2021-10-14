import { FormsModule } from '@angular/forms';
import { EmployeeService } from './../employee.service';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing'

import { CreateEmployeeComponent } from './create-employee.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('CreateEmployeeComponent', () => {
  let component: CreateEmployeeComponent;
  let fixture: ComponentFixture<CreateEmployeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        imports : [HttpClientTestingModule, FormsModule, RouterTestingModule.withRoutes([])],
        providers : [EmployeeService],
        declarations: [ CreateEmployeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

    it('should check form submit triggering onSubmit method', async(() => {
        spyOn(component, 'onSubmit');
    
        let button = fixture.debugElement.nativeElement.querySelector('.btn');
        button.click();
    
        fixture.whenStable().then(() => {
        expect(component.onSubmit).toHaveBeenCalled();
        });
    }));

    it('should check  onSubmit triggering saveEmployee method', async(() => {
        spyOn(component, 'saveEmployee');
    
        let button = fixture.debugElement.nativeElement.querySelector('.btn');
        button.click();
    
        fixture.whenStable().then(() => {
        expect(component.saveEmployee).toHaveBeenCalled();
        });
    }));

});
