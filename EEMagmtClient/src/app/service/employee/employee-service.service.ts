import { Injectable } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Employee } from '../../vo/Employee';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeServiceService {

  constructor(private http:HttpClient) { }

  baseUrl = environment.baseUrl;

  getEmployeeList(minSalary:number, maxSalary:number, offset:number, limit:number, sort:string){
    let params = new HttpParams().set('minSalary',minSalary.toString())
                                .set('maxSalary',maxSalary.toString())
                                .set('offset',offset.toString())
                                .set('limit',limit.toString())
                                .set('sort',sort);
    return this.http.get(this.baseUrl+'/users',{params:params});
    // return this.http.get(encodeURI(this.baseUrl+'/employee/getEmployeeList?minSalary='+minSalary.toString()+'&maxSalary='+maxSalary.toString()+'&offset='+offset.toString()+'&limit='+limit.toString()+'&sort='+sort));
  }

  updateEmployee(employee:Employee){
    return this.http.patch<any>(this.baseUrl+'/users/'+employee.id, employee);
  }

  addEmployee(employee:Employee){
    return this.http.post<any>(this.baseUrl+'/users', employee);
  }

  uploadEmployees(formData:FormData){
    return this.http.post<any>(this.baseUrl+'/users/upload', formData);
  }

  deleteEmployee(employee:Employee){
    return this.http.delete<any>(this.baseUrl+'/users/'+employee.id);
  }
}
