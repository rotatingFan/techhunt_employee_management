import { Component, OnInit,ViewChild } from '@angular/core';
import {EmployeeServiceService } from '../service/employee/employee-service.service';
import {Employee} from '../vo/Employee';
import {JSONResponse} from '../vo/JSONResponse';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {EditEmployeeComponent} from '../edit-employee/edit-employee.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.component.html',
  styleUrls: ['./view-employee.component.css']
})
export class ViewEmployeeComponent implements OnInit {

  constructor(private employeeService:EmployeeServiceService, public dialog: MatDialog) { }

  employeeList:Employee[] = [];

  displayedColumns: string[] = ['id', 'name', 'login', 'salary','action'];
  dataSource = new MatTableDataSource(this.employeeList);

  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit(): void {
    this.dataSource.sort = this.sort;
    this.employeeService.getEmployeeList().subscribe((resp:JSONResponse)=>{
      if(resp.status == 'success'){
        this.employeeList = resp.data;
        this.dataSource.data = this.employeeList;
      }
    });
  }

  editEmployee(employee:Employee){
    this.dialog.open(EditEmployeeComponent, {
      data: {
        employee: employee
      }
    });
  }
}
