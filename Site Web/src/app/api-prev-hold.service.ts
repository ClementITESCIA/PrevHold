import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { OnInit } from '@angular/core';

const url = 'http://127.0.0.1:3000/prevhold/';

const HttpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class ApiPrevHoldService {

  constructor(private http : HttpClient) { }

  /*getPrevhold(){
    return this.http.get(url);
  }*/

  envoieContact(form){
    return this.http.post(url, form, HttpOptions);
  }

  mdpAdd(form){
    return this.http.post(url, form, HttpOptions);
  }

  inscriptionAdd(form){
    return this.http.post(url, form, HttpOptions);
  }
}
