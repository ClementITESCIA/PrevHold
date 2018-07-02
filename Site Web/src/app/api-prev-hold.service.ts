import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OnInit } from '@angular/core';

const url = 'http://127.0.0.1:3000/prevhold/';

@Injectable()
export class ApiPrevHoldService {

  constructor(private http : HttpClient) { }

  getPrevhold(){
    return this.http.get(url);
  }

  /*submitAdd(form){
    return this.http.post(url);
  }*/

}
