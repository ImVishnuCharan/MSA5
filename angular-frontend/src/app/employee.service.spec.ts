import { HttpErrorResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
 
import { EmployeeService } from './employee.service';
import { Employee } from '././employee'

describe('EmployeeService', () => {
  let service: EmployeeService;
  let httpTestingController : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [HttpClientTestingModule],
      providers : [EmployeeService]
    });
    service = TestBed.inject(EmployeeService);
  });

  beforeEach(() => {
    service = TestBed.inject(EmployeeService);
    httpTestingController = TestBed.inject(HttpTestingController);
  })

  afterEach(() => {
    httpTestingController.verify();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it ('should test HttpClient.GET - Get Employees List', () => {
    const testEmployees : Employee[] = [
      {id : 1, firstName : "John", lastName : "Smith", emailId : "jsmith@gmail.com"},
      {id : 2, firstName : "Jane", lastName : "Doe", emailId : "jdoe@gmail.com"},
    ]
    service.getEmployeesList().subscribe((employees)=>{
      expect(employees).toBe(testEmployees, 'should check mocked data');
    });

    const req = httpTestingController.expectOne(service.baseURL);

    expect(req.cancelled).toBeFalsy();
    expect(req.request.responseType).toEqual('json');

    req.flush(testEmployees);
    
  });

  it ('should test HttpClient.GET - Get Employee By Id', () => {
    const testEmployee : Employee = {id : 1, firstName : "John", lastName : "Smith", emailId : "jsmith@gmail.com"};
    let id = 1;
    service.getEmployeeById(id).subscribe((employee)=>{
      expect(employee).toBe(testEmployee, 'should check mocked data');
    });

    const req = httpTestingController.expectOne(service.baseURL +  '/' + id);

    expect(req.cancelled).toBeFalsy();
    expect(req.request.responseType).toEqual('json');

    req.flush(testEmployee);
  });

  it ('should test HttpClient.POST - Create Employee', () =>{
    const newEmployee : Employee = {id : 4, firstName : "Jane", lastName : "Doe", emailId : "jdoe@gmail.com"};
    
    service.createEmployee(newEmployee).subscribe((employee)=>{
      expect(employee).toBe(newEmployee, 'should check mocked data');
    });

    const req = httpTestingController.expectOne(service.baseURL);

    expect(req.cancelled).toBeFalsy();
    expect(req.request.responseType).toEqual('json');

    req.flush(newEmployee);
  });

  it ('should test HttpClient.PUT - Update Employee by Id', () => {
    const updatedEmployee : Employee = {id : 2, firstName : "Jane", lastName : "Doe", emailId : "jdoe@gmail.com"};
    let id = 2;
    service.updateEmployee(id, updatedEmployee).subscribe((employee)=>{
      expect(employee).toBe(updatedEmployee, 'should check mocked data');
    });

    const req = httpTestingController.expectOne(service.baseURL + "/" + id);

    expect(req.cancelled).toBeFalsy();
    expect(req.request.responseType).toEqual('json');

    req.flush(updatedEmployee);
  });

  it ('should test HttpClient.DELETE - Delete Employee by Id', () =>{
    let id = 1;
    service.deleteEmployee(id).subscribe((res)=>{
      expect(res).toBeTruthy();
    });

    const req = httpTestingController.expectOne(service.baseURL + "/" + id);

    expect(req.cancelled).toBeFalsy();
    expect(req.request.responseType).toEqual('json');

    req.flush(id);
  })

  it ('should test 404 error', () => {
    const errorMsg = 'mock 404 error occured';
    service.getEmployeesList().subscribe( (data) => {
      fail('failing with 404 error');
    },
    (error : HttpErrorResponse) => {
      expect(error.status).toEqual(404);
      expect(error.error).toEqual(errorMsg);
    }
    );

    const req = httpTestingController.expectOne(service.baseURL);
    req.flush(errorMsg, {status: 404, statusText: 'Not Found'});

  });

});
