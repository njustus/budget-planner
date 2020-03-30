import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingComponent } from './landing/landing.component';

//TOOD: guard for dashboard, ...
//redirect to login if no token available

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/welcome'
  },
  {
    path: 'welcome',
    component: LandingComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
