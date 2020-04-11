import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { BudgetPlannerComponent } from './budget-planner.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterModule, Route } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material';
import { AuthenticationInterceptor } from '../authentication.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BudgetComponent } from './budget/budget.component';
import { SharedModule } from '../shared/shared.module';
import { BudgetPlannerRoutingModule } from './budget-planner-routing.module';
import { PaymentListComponent } from './payment-list/payment-list.component';
import { EditPaymentDialogComponent } from './edit-payment-dialog/edit-payment-dialog.component';

@NgModule({
  declarations: [
    BudgetPlannerComponent,
    BudgetComponent,
    DashboardComponent,
    PaymentListComponent,
    EditPaymentDialogComponent
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
  ]
})
export class BudgetPlannerModule { }
