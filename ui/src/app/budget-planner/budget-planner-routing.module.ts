import { BudgetPlannerComponent } from './budget-planner.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BudgetComponent } from './budget/budget.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EditPaymentDialogComponent} from './edit-payment-dialog/edit-payment-dialog.component';

const routes: Routes = [
  {
    path: 'budget-planner',
    component: BudgetPlannerComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      },
      {
        path: 'budgets/:budgetId',
        component: BudgetComponent
      },
      {
        path: 'budget-payment',
        component: EditPaymentDialogComponent
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
