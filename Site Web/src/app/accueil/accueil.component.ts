import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent implements OnInit {
  latitude = 49.041223;
  longitude = 2.029123;

  constructor() { }

  ngOnInit() {
  }

}
