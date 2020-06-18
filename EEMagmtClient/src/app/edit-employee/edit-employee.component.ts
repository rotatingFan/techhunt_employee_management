import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmployeeServiceService } from '../service/employee/employee-service.service';
import { Employee } from '../vo/Employee';
import { JSONResponse } from '../vo/JSONResponse';
import { MatDialogRef } from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';
import {AlertComponent} from '../alert/alert.component';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.css']
})
export class EditEmployeeComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {employee:Employee,action:string}, private employeeService:EmployeeServiceService,public dialog: MatDialog, private dialogRef: MatDialogRef<EditEmployeeComponent>, private snackBar: MatSnackBar) { }

  employeeForm = new FormGroup({
    // id: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    login: new FormControl('', Validators.required),
    salary: new FormControl('', Validators.required),
  });

  action:string = null;
  employee:Employee = null;

  ngOnInit(): void {
    console.log(this.data);
    this.action = this.data.action;
    this.employee = this.data.employee;
    if(this.action == 'update'){
      this.employeeForm.patchValue(this.data.employee);
    }
    this.employeeForm.markAsUntouched();
  }

  clearInput(input:string){
    this.employeeForm.get(input).reset();
  }

  updateEmployee(){
    let updatedEmployee:Employee = new Employee(this.employeeForm.value);
    updatedEmployee.version = this.employee.version;
    updatedEmployee.id = this.employee.id;
    this.employeeService.updateEmployee(updatedEmployee).subscribe((resp:JSONResponse)=>{
      if(resp.status == 'fail'){
        this.showError(resp.errorMessage.split('|').join('\r\n'))
      }else{
        this.dialogRef.close();
        this.snackBar.open('Employee updated','Close',{
          duration: 1500
        });
      }
    });
  }

  addEmployee(){
    let addEmployee:Employee = new Employee(this.employeeForm.value);
    this.employeeService.addEmployee(addEmployee).subscribe((resp:JSONResponse)=>{
      if(resp.status == 'fail'){
        this.showError(resp.errorMessage.split('|').join('\r\n'))
      }else{
        this.dialogRef.close();
        this.snackBar.open('Employee added','Close',{
          duration: 1500
        });
      }
    });
  }

  deleteEmployee(){
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
        this.employeeService.deleteEmployee(this.employee).subscribe(resp=>{
          if(resp.status == 'success'){
            this.dialogRef.close();
            this.snackBar.open('Employee deleted','Close',{
              duration: 1500
            });
          }
        });
      }
    });
  }

  showError(message:string){
    this.dialog.open(AlertComponent, {
      data: {
        title: 'Error',
        message:message,
        button:false
      }
    });
  }

}
