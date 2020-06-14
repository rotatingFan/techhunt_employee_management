import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmployeeServiceService } from '../service/employee/employee-service.service';
import { Employee } from '../vo/Employee';
import { JSONResponse } from '../vo/JSONResponse';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.css']
})
export class EditEmployeeComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {employee:Employee}, private employeeService:EmployeeServiceService, private dialogRef: MatDialogRef<EditEmployeeComponent >) { }

  employeeForm = new FormGroup({
    id: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    login: new FormControl('', Validators.required),
    salary: new FormControl('', Validators.required),
  });

  ngOnInit(): void {
    console.log(this.data);
    this.employeeForm.patchValue(this.data.employee);
    this.employeeForm.markAsUntouched();
  }

  clearInput(input:string){
    this.employeeForm.get(input).reset();
  }

  updateEmployee(){
    let updatedEmployee:Employee = new Employee(this.employeeForm.value);
    updatedEmployee.version = this.data.employee.version;
    this.employeeService.updateEmployee(updatedEmployee).subscribe((resp:JSONResponse)=>{
      if(resp.status == 'fail'){
        alert(resp.errorMessage.split('|').join('\r\n'));
      }else{
        this.dialogRef.close();
        alert('Update success');
      }
    });
  }

}
