import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { AccueilComponent } from './accueil/accueil.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ContactComponent } from './contact/contact.component';
import { PlanningComponent } from './planning/planning.component';
import { FicheComponent } from './fiche/fiche.component';
import { ProfilComponent } from './profil/profil.component';

export const router: Routes = [
    { path: 'accueil', component: AccueilComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'contact', component: ContactComponent },
    { path: 'planning_as', component: PlanningComponent },
    { path: 'fiche_suivi', component: FicheComponent },
    { path: 'profil_med', component: ProfilComponent },
];

export const routes: ModuleWithProviders = RouterModule.forRoot(router);
