import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EmployeeServiceService } from '../service/employee/employee-service.service';

@Component({
  selector: 'app-upload-employee-list',
  templateUrl: './upload-employee-list.component.html',
  styleUrls: ['./upload-employee-list.component.css']
})
export class UploadEmployeeListComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private employeeService: EmployeeServiceService) { }
  uploadForm: FormGroup;

  ngOnInit(): void {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.uploadForm.get('profile').setValue(file);
    }
  }

  onSubmit() {
    console.log('submit');
    const formData = new FormData();
    formData.append('file', this.uploadForm.get('profile').value);
    this.employeeService.uploadEmployees(formData).subscribe(resp =>{
      
    });
  }

}
