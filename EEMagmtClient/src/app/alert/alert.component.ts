import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {title:string,message:string, button:boolean, buttonWord:string}, private dialogRef: MatDialogRef<AlertComponent>) { }
  title:string ='';
  message:string = '';
  button:boolean = true;
  buttonWord:string = 'Delete';

  ngOnInit(): void {
    this.title = this.data.title;
    this.message = this.data.message;
    this.button = this.data.button;
    this.buttonWord = this.data.buttonWord;
  }
}
