import { Component, OnInit, ViewChild } from '@angular/core';
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
  contact_data;

  @ViewChild('submitForm') public submitForm: any;

  constructor(private api: ApiPrevHoldService) { }

  ngOnInit() {
    //this.getPrevhold();
  }

  /*getPrevhold() {
   this.api.getPrevhold()
     .subscribe(
       data => {
         console.log(JSON.stringify(data));
         this.result = data
       },
       err => console.log(JSON.stringify(err)),
       () => console.log('error')
     );
   }*/

   submitAdd(form){
     console.log(JSON.stringify(form));
     this.api.submitAdd(form)
     .subscribe(
       data => {
         console.log(JSON.stringify(data));
         this.contact_data = data
       },
       err => console.log(JSON.stringify(err)),
       () => console.log('error')
     );
     alert(`Votre message a bien été envoyé !`);
     this.submitForm.reset();
   }

}
