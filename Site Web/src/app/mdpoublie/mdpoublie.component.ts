import { Component, OnInit } from '@angular/core';
import { ApiPrevHoldService } from '../api-prev-hold.service';

@Component({
  selector: 'app-mdpoublie',
  templateUrl: './mdpoublie.component.html',
  styleUrls: ['./mdpoublie.component.scss']
})
export class MdpoublieComponent implements OnInit {

  mdp_data;

  constructor(private api_mdp: ApiPrevHoldService) { }

  ngOnInit() {
  }

  mdpAdd(form){
    console.log(JSON.stringify(form));
    this.api_mdp.mdpAdd(form)
    .subscribe(
      data => {
        console.log(JSON.stringify(data));
        this.mdp_data = data
      },
      err => console.log(JSON.stringify(err)),
      () => console.log('error')
    );
    alert(`Votre message a bien été envoyé !`);
  }
}
