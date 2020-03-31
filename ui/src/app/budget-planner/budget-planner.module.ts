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
import { AuthenticationInterceptor } from '../authentication.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BudgetComponent } from './budget/budget.component';
import { SharedModule } from '../shared/shared.module';

const routes: Route[] = [
  {
    path: 'budget-planner',
    component: BudgetPlannerComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      },
      {
        path: 'budget/:budgetId',
        component: BudgetComponent
      }
    ]
  }
]

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule],
  declarations: [BudgetComponent]
})
export class BudgetPlannerRoutingModule {}

@NgModule({
  declarations: [BudgetPlannerComponent, DashboardComponent],
  imports: [
    BudgetPlannerRoutingModule,
    CommonModule,
    SharedModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    }
  ]
})
export class BudgetPlannerModule { }
