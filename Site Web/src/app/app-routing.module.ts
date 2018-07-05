import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { AccueilComponent } from './accueil/accueil.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PlanningComponent } from './planning/planning.component';
import { FicheComponent } from './fiche/fiche.component';
import { ConnexionComponent } from './connexion/connexion.component';
import { MdpoublieComponent } from './mdpoublie/mdpoublie.component';
import { InscriptionComponent } from './inscription/inscription.component';

export const router: Routes = [
    { path: '', component: ConnexionComponent },
    { path: 'accueil', component: AccueilComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'planning_as', component: PlanningComponent },
    { path: 'fiche_suivi', component: FicheComponent },
    { path: 'mdp_forget', component:  MdpoublieComponent},
    { path: 'inscription', component:  InscriptionComponent}
];

export const routes: ModuleWithProviders = RouterModule.forRoot(router);
