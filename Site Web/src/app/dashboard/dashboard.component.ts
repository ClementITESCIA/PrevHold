import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { HttpClient } from '@angular/common/http';
import { dataCapteur1 } from './dataPrevhold';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private http: HttpClient) {}

  lineChartDouche: any;
  barChartSortie: any;
  doughnutChartFumee: any;
  doughnutChartChute: any;
  lineChartTV: any;
  lineChartVolets: any;

  //----- Prise de douches ------
  allData: Array<dataCapteur1> = [];
  alljours = [];
  alloccurrence = [];
  //-----------------------------

  sortieData(){
    this.barChartSortie = new Chart('barChart', {
      type: 'bar',
      data: {
        labels: this.alljours,
        datasets: [
          {
            label: 'Sortie',
            data: this.alloccurrence,
            backgroundColor: '#EC4F4F',
            borderColor: '#EC4F4F',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                // fonction pour la configuration de l'axe
                userCallback: function(label) {
                // quand il y a une même valeur (entier et décimal), le nombre entier est pris en compte
                if (Math.floor(label) === label) {
                  return label;
                  }
                },
              }
            }
          ]
        }
      }
    })
  }

  doucheData(){
    this.lineChartDouche = new Chart('lineChart', {
    type: 'line',
    data: {
      labels: this.alljours,
      datasets: [
          {
              label: 'Nombre de douches prises',
              data: this.alloccurrence,
              fill: false,
              backgroundColor: '#4BC0C0',
              borderColor: '#4BC0C0'
          }
        ]
    },
    options: {
      responsive: true,
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
              // fonction pour la configuration de l'axe
              userCallback: function(label) {
              // quand il y a une même valeur (entier et décimal), le nombre entier est pris en compte
              if (Math.floor(label) === label) {
                return label;
                }
              },
            }
          }
        ]
      }
    }
  })
}

  fumeeData(){
    this.doughnutChartFumee = new Chart('doughnutChart', {
      type: 'doughnut',
      data: {
        labels: ['Avril', 'Mai', 'Juin', 'Juillet'],
        datasets: [
          {
            data: [1, 3, 1, 1],
            backgroundColor: ["#F7464A", "#F3AEAB", "#FDB45C", "#0349CA"],
            borderColor: ["#F7464A", "#F3AEAB", "#FDB45C", "#0349CA"],
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
      }
    })
  }

  chuteData(){
    this.doughnutChartChute = new Chart('doughnutChart_1', {
      type: 'doughnut',
      data: {
        labels: ['Avril', 'Mai', 'Juin', 'Juillet'],
        datasets: [
          {
            data: [2, 1, 5, 2],
            backgroundColor: ["#0349CA", "#F2F2F2", "#FF0000", "#E5CCFF"],
            borderColor: ["#0349CA", "#F2F2F2", "#FF0000", "#E5CCFF"],
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
      }
    })
  }

  tvData(){
    this.lineChartTV = new Chart('lineChart1', {
      type: 'line',
      data: {
        labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
        datasets: [
            {
                label: 'Temps de visionnage de cette semaine',
                data: [2, 4, 0, 3, 2, 1, 3],
                fill: false,
                backgroundColor: '#28D850',
                borderColor: '#28D850'
            },
            {
                label: 'Temps de visionnage de la semaine dernière',
                data: [1, 2, 1, 2.3, 2.8, 1.4, 3.7],
                fill: false,
                backgroundColor: '#C674DE',
                borderColor: '#C674DE'
            }
          ]
      },
      options: {
        responsive: true,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                // fonction pour la configuration de l'axe
                userCallback: function(label) {
                // quand il y a une même valeur (entier et décimal), le nombre entier est pris en compte
                if (Math.floor(label) === label) {
                  return label;
                  }
                },
              }
            }
          ]
        }
      }
    })
  }

  voletsData(){
    this.lineChartVolets = new Chart('lineChart_1', {
    type: 'line',
    data: {
      labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
      datasets: [
          {
              label: 'Ouverture des volets',
              data: [1, 3, 3, 2, 4, 1, 1],
              fill: false,
              backgroundColor: '#33CC33',
              borderColor: '#33CC33'
          }
        ]
    },
    options: {
      responsive: true,
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
              // fonction pour la configuration de l'axe
              userCallback: function(label) {
              // quand il y a une même valeur (entier et décimal), le nombre entier est pris en compte
              if (Math.floor(label) === label) {
                return label;
                }
              },
            }
          }
        ]
      }
    }
  })
  }

  ngOnInit(){
    this.http.get('http://localhost:3000/mesurejournaliere?capteurId=1')
    .subscribe( resp => {
      console.log(resp)
      for(let i in resp){
        this.allData.push(new dataCapteur1(resp[i].jour, resp[i].occurence));
        this.alljours[i] = resp[i].jour;
        this.alloccurrence[i] = resp[i].occurence;
        console.log(this.alljours[i]);
        this.doucheData();
        this.sortieData();
        this.fumeeData();
        this.chuteData();
        this.tvData();
        this.voletsData();
      }
    })
  }
}
