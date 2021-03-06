import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { AgmCoreModule } from '@agm/core';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { routes } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { ApiPrevHoldService } from './api-prev-hold.service';


import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PlanningComponent } from './planning/planning.component';
import { FicheComponent } from './fiche/fiche.component';
import { AccueilComponent } from './accueil/accueil.component';
import { ConnexionComponent } from './connexion/connexion.component';
import { MdpoublieComponent } from './mdpoublie/mdpoublie.component';
import { InscriptionComponent } from './inscription/inscription.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    PlanningComponent,
    FicheComponent,
    AccueilComponent,
    ConnexionComponent,
    MdpoublieComponent,
    InscriptionComponent,

  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    routes,
    HttpClientModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBdFJtzibaf1vpgryV4Od9hwXo-giGWZk0'
    })
  ],
  schemas: [NO_ERRORS_SCHEMA],
  providers: [
    ApiPrevHoldService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
