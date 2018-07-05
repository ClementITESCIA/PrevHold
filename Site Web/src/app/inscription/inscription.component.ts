import { Component, OnInit } from '@angular/core';
import { ApiPrevHoldService } from '../api-prev-hold.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent implements OnInit {

  inscription_data;
  constructor(private api_inscription: ApiPrevHoldService) { }

  ngOnInit() {
  }

  inscriptionAdd(form){
    console.log(JSON.stringify(form));
    this.api_inscription.inscriptionAdd(form)
    .subscribe(
      data => {
        console.log(JSON.stringify(data));
        this.inscription_data = data
      },
      err => console.log(JSON.stringify(err)),
      () => console.log('error')
    );
    alert(`Vous êtes bien inscrit`);
  }
}
