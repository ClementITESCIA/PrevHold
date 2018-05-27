import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { routes } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactComponent } from './contact/contact.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PlanningComponent } from './planning/planning.component';
import { FicheComponent } from './fiche/fiche.component';
import { ProfilComponent } from './profil/profil.component';
import { AccueilComponent } from './accueil/accueil.component';


@NgModule({
  declarations: [
    AppComponent,
    ContactComponent,
    DashboardComponent,
    PlanningComponent,
    FicheComponent,
    ProfilComponent,
    AccueilComponent
  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    routes
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
