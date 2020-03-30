import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BudgetPlannerComponent } from './budget-planner.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterModule, Route } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { MatListModule } from '@angular/material';

const routes: Route[] = [
  {
    path: 'budget-planner',
    component: BudgetPlannerComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      }
    ]
  }
]

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class BudgetPlannerRoutingModule {}

@NgModule({
  declarations: [BudgetPlannerComponent, DashboardComponent],
  imports: [
    BudgetPlannerRoutingModule,
    CommonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule
  ]
})
export class BudgetPlannerModule { }
