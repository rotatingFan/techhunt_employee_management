import { Component, OnInit,ViewChild } from '@angular/core';
import {EmployeeServiceService } from '../service/employee/employee-service.service';
import {Employee} from '../vo/Employee';
import {JSONResponse} from '../vo/JSONResponse';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {EditEmployeeComponent} from '../edit-employee/edit-employee.component';
import {MatDialog} from '@angular/material/dialog';
import {PageEvent} from '@angular/material/paginator';
import {Sort} from '@angular/material/sort';
import { Subscription } from 'rxjs';
import {UploadEmployeeListComponent} from '../upload-employee-list/upload-employee-list.component';
import {AlertComponent} from '../alert/alert.component';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.component.html',
  styleUrls: ['./view-employee.component.css']
})
export class ViewEmployeeComponent implements OnInit {

  constructor(private employeeService:EmployeeServiceService, public dialog: MatDialog, private snackBar: MatSnackBar) { }

  employeeList:Employee[] = [];

  displayedColumns: string[] = ['id', 'name', 'login', 'salary','action'];
  dataSource = new MatTableDataSource(this.employeeList);
  // MatPaginator Output
  //filter
  minSalary:number = 0;
  maxSalary:number = 4000;
  offset:number = 0;
  limit:number = 30;
  sortFilter:string = 'name';
  sortAsc:string = '+';
  pageLength:number = 0;

  getEmployeeSub:Subscription = null;

  @ViewChild(MatSort, {static: true}) sort: MatSort;

  ngOnInit(): void {
    this.dataSource.sort = this.sort;
    
    this.getEmployeeList();
  }

  sortData(sort: Sort){
    console.log(sort);
    if(sort.active == 'action'){
      return;
    }
    this.sortFilter = sort.active;
    console.log(sort.direction);
    if(sort.direction == ''){
      return;
    }
    if(sort.direction == 'asc'){
      console.log('acending');
      this.sortAsc='+';
    }else{
      console.log('decending');
      this.sortAsc = '-';
    }
    
    this.getEmployeeList();
    return 0;
  }

  pageData(pageEvent: PageEvent){
    console.log(pageEvent);
    this.limit=pageEvent.pageSize;
    this.offset=pageEvent.pageIndex;
    this.getEmployeeList();
  }

  getEmployeeList(){
    if(this.getEmployeeSub){
      this.getEmployeeSub.unsubscribe();
    }
    this.dataSource = new MatTableDataSource([]);
    console.log(this.sortAsc + this.sortFilter);
    this.getEmployeeSub =  this.employeeService.getEmployeeList(this.minSalary, this.maxSalary, this.offset, this.limit, this.sortAsc + this.sortFilter).subscribe((resp:any)=>{
      if(resp.results){
        this.employeeList = resp.results;
        this.dataSource.data = this.employeeList;
        this.pageLength = resp.maxPage;
      }
    });
  }

  editEmployee(employee:Employee){
    let dialogRef = this.dialog.open(EditEmployeeComponent, {
      data: {
        employee: employee,
        action:'update'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getEmployeeList();
    });
  }

  addEmployee(){
    let dialogRef = this.dialog.open(EditEmployeeComponent, {
      data: {
        employee: null,
        action:'add'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getEmployeeList();
    });
  }

  deleteEmployee(employee:Employee){
    let dialogRef = this.dialog.open(AlertComponent, {
      data: {
        title: 'Delete Employee',
        message:'Are you sure you want to delete this employee?',
        button:true,
        buttonWord:'Delete'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result == true){
        this.employeeService.deleteEmployee(employee).subscribe(resp=>{
          if(resp.status == 'success'){
            this.getEmployeeList();
            this.snackBar.open('Employee deleted','Close',{
              duration: 1500
            });
          }
        });
      }
    });
  }

  uploadEmployee(){
    let dialogRef = this.dialog.open(UploadEmployeeListComponent, {
      data: {}
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getEmployeeList();
    });
  }
}
