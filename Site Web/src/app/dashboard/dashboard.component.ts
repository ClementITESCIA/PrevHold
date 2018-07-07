import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  LineChart: any;
  BarChart: any;
  LineChart1: any;
  DoughnutChart_1: any;
  DoughnutChart: any;
  LineChart_1: any;

  constructor() {}

  ngOnInit(){
    this.LineChart = new Chart('lineChart', {
    type: 'line',
    data: {
      labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
      datasets: [
          {
              label: 'Nom de douches prises',
              data: [2, 1, 0, 2, 0, 1, 3],
              fill: false,
              backgroundColor: '#4BC0C0',
              borderColor: '#4BC0C0'
          }
        ]
    },
    options: {
      responsive: false,
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

  this.BarChart = new Chart('barChart', {
    type: 'bar',
    data: {
      labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
      datasets: [
        {
          label: 'Sortie',
          data: [1, 0, 3, 2, 3, 1, 4],
          backgroundColor: '#EC4F4F',
          borderColor: '#EC4F4F',
          borderWidth: 1
        }
      ]
    },
    options: {
      responsive: false,
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: true
            }
          }
        ]
      }
    }
  })

  this.LineChart1 = new Chart('lineChart1', {
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
      responsive: false,
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

  this.DoughnutChart = new Chart('doughnutChart', {
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
      responsive: false,
    }
  })

  this.DoughnutChart_1 = new Chart('doughnutChart_1', {
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
      responsive: false,
    }
  })

    this.LineChart_1 = new Chart('lineChart_1', {
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
      responsive: false,
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
}
