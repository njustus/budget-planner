import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { BudgetPlannerComponent } from './budget-planner.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AuthenticationInterceptor } from '../authentication.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BudgetComponent } from './budget/budget.component';
import { SharedModule } from '../shared/shared.module';
import { BudgetPlannerRoutingModule } from './budget-planner-routing.module';
import { PaymentListComponent } from './payment-list/payment-list.component';
import { EditPaymentDialogComponent } from './edit-payment-dialog/edit-payment-dialog.component';
import { EditBudgetDialogComponent } from './edit-budget-dialog/edit-budget-dialog.component';
import { InvideInvestorDialogComponent } from './invide-investor-dialog/invide-investor-dialog.component';

@NgModule({
  declarations: [
    BudgetPlannerComponent,
    BudgetComponent,
    DashboardComponent,
    PaymentListComponent,
    EditPaymentDialogComponent,
    EditBudgetDialogComponent,
    InvideInvestorDialogComponent
  ],
  imports: [
    BudgetPlannerRoutingModule,
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    }
  ],
  entryComponents: [
    EditPaymentDialogComponent,
    EditBudgetDialogComponent,
    InvideInvestorDialogComponent
  ]
})
export class BudgetPlannerModule { }
