import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http'

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent implements OnInit {
  public data: any=[]
  latitude = 49.041223;
  longitude = 2.029123;

  ngOnInit() {
  }

  constructor(private http: HttpClient){
  }

  save(name, email, mobile, subject, message): void {
    this.data['name']= name;
                this.data['email']= email;
                this.data['mobile']= mobile;
                this.data['subject']= subject;
                this.data['message']= message;
    console.log(this.data);
                //add request to send email or into mysql
                this.http.put<any>('http://localhost/api/v1/update/', this.data).subscribe(
        res => {
          console.log(res);
      },
      (err: HttpErrorResponse) => {
        if (err.error instanceof Error) {
          console.log("Client-side error occured.");
        } else {
          console.log("Server-side error occurred.");
        }
      });
   }
}
