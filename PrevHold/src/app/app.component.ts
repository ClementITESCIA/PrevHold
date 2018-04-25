import { Component } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
  selector: 'PT-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'PrevHold';
  BarChart: any;
  PieChart: any;
  LineChart: any;
  LineChart1: any;

  constructor(){}

  ngOnInit() {
    this.BarChart = new Chart('barChart', {
      type: 'bar',
      data: {
        labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
        datasets: [
          {
            label: "Entrée",
            data: [4, 7, 1, 3, 4, 5, 2],
            backgroundColor: '#DE4141',
            borderColor: '#E23232',
            borderWidth: 1
          },
          {
            label: 'Sortie',
            data: [4, 6, 5, 6, 4, 5, 2],
            backgroundColor: '#32AFE2',
            borderColor: '#1D90BF',
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

    this.LineChart = new Chart('lineChart', {
      type: 'line',
      data: {
        labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
        datasets: [
            {
                label: 'Nom de douche prise',
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

  }
}
