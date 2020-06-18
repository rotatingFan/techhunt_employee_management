import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EmployeeServiceService } from '../service/employee/employee-service.service';
import { MatDialogRef } from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';
import {AlertComponent} from '../alert/alert.component';

@Component({
  selector: 'app-upload-employee-list',
  templateUrl: './upload-employee-list.component.html',
  styleUrls: ['./upload-employee-list.component.css']
})
export class UploadEmployeeListComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private employeeService: EmployeeServiceService, public dialog: MatDialog, private dialogRef: MatDialogRef<UploadEmployeeListComponent>, private snackBar: MatSnackBar) { }
  uploadForm: FormGroup;

  @ViewChild('fileUpload')
  _fileInput: ElementRef;

  ngOnInit(): void {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      let fileExt = event.target.files[0].name.split('.');
      if(fileExt[fileExt.length-1] =='csv'){
        const file = event.target.files[0];
        this.uploadForm.get('profile').setValue(file);
      }else{
        this._fileInput.nativeElement.value = '';
        this.snackBar.open('Only .csv file allowed','Close',{
          duration: 1500
        });
      }
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.uploadForm.get('profile').value);
    this.employeeService.uploadEmployees(formData).subscribe(resp =>{
      if(resp.status == 'success'){
        this.dialogRef.close();
        this.snackBar.open('File successfully uploaded','Close',{
          duration: 1500
        });
      }else{
        this.showError(resp.data.length + ' errors found');
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
