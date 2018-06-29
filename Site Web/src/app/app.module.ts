import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { AgmCoreModule } from '@agm/core';

import { routes } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PlanningComponent } from './planning/planning.component';
import { FicheComponent } from './fiche/fiche.component';
import { ProfilComponent } from './profil/profil.component';
import { AccueilComponent } from './accueil/accueil.component';
import { ConnexionComponent } from './connexion/connexion.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    PlanningComponent,
    FicheComponent,
    ProfilComponent,
    AccueilComponent,
    ConnexionComponent
  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    routes,
    FormsModule,
    HttpClientModule
    /*AgmCoreModule.forRoot({
      apiKey:
    })*/
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
