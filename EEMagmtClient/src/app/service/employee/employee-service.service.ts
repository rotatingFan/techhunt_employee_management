import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee } from '../../vo/Employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeServiceService {

  constructor(private http:HttpClient) { }

  baseUrl = 'http://localhost:8080';

  getEmployeeList(){
    return this.http.get(this.baseUrl+'/employee/getEmployeeList');
  }

  updateEmployee(employee:Employee){
    return this.http.post<any>(this.baseUrl+'/employee/updateEmployee', employee);
  }
}
