import { Component, OnInit } from '@angular/core';
import { ApiPrevHoldService } from '../api-prev-hold.service';

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent implements OnInit {
  latitude = 49.041223;
  longitude = 2.029123;
  result;
  result1;
  constructor(private api: ApiPrevHoldService) { }

  ngOnInit() {
    this.getPrevhold();
    //this.submitAdd(form);
  }


  getPrevhold() {
   this.api.getPrevhold()
     .subscribe(
       data => {
         console.log(JSON.stringify(data));
         this.result = data
       },
       err => console.log(JSON.stringify(err)),
       () => console.log('error')
     );
   }

   /*submitAdd(form){
     console.log(JSON.stringify(form));
     this.api.submitAdd(form)
     .subscribe(
       data => {
         console.log(JSON.stringify(data));
         this.result1 = data
       },
       err => console.log(JSON.stringify(err)),
       () => console.log('error')
     );
   }*/
}
