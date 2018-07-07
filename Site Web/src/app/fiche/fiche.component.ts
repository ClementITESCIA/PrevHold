import { Component, OnInit } from '@angular/core';
import * as jsPDF from 'jspdf';

@Component({
  selector: 'app-fiche',
  templateUrl: './fiche.component.html',
  styleUrls: ['./fiche.component.scss']
})
export class FicheComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  downloadPDF_1(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Mars du 1 au 15', 10, 10);

    doc.save('Fiche_Suivi_Mars');
  }

  downloadPDF_2(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Mars du 16 au 31', 10, 10);

    doc.save('Fiche_Suivi_Mars_2');
  }

  downloadPDF_3(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Avril du 1 au 15', 10, 10);

    doc.save('Fiche_Suivi_Avril');
  }

  downloadPDF_4(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Avril du 16 au 30', 10, 10);

    doc.save('Fiche_Suivi_Avril_2');
  }

  downloadPDF_5(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Mai du 1 au 15', 10, 10);

    doc.save('Fiche_Suivi_Mai');
  }

  downloadPDF_6(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Mai du 16 au 31', 10, 10);

    doc.save('Fiche_Suivi_Mai_2');
  }

  downloadPDF_7(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Juin du 1 au 15', 10, 10);

    doc.save('Fiche_Suivi_Juin');
  }

  downloadPDF_8(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Juin du 16 au 30', 10, 10);

    doc.save('Fiche_Suivi_Juin_2');
  }

  downloadPDF_9(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Mai du 1 au 15', 10, 10);

    doc.save('Fiche_Suivi_Juillet');
  }

  downloadPDF_10(){
    const doc = new jsPDF();
    doc.text('Fiche de Suivi Mai du 16 au 31', 10, 10);

    doc.save('Fiche_Suivi_Juillet_2');
  }
}
