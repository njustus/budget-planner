import { Component, OnInit } from '@angular/core';
import {Budget, Account} from '../models'
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { EditPaymentDialogComponent } from './edit-payment-dialog/edit-payment-dialog.component';
import { BudgetPlannerService } from './budget-planner.service';
import { DefaultService } from 'generated-src';
import { ActivatedRoute } from '@angular/router';
import { map, tap, share } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { EditBudgetDialogComponent } from './edit-budget-dialog/edit-budget-dialog.component';
import { InvideInvestorDialogComponent } from './invide-investor-dialog/invide-investor-dialog.component';

@Component({
  selector: 'app-budget-planner',
  templateUrl: './budget-planner.component.html',
  styleUrls: ['./budget-planner.component.scss'],
  providers: [BudgetPlannerService]
})
export class BudgetPlannerComponent implements OnInit {

  budgets: Budget[] = []
  focusedBudget: Budget | undefined = undefined
  focusedAccount: Account | undefined = undefined

  constructor(private readonly apiSvc: DefaultService,
              private readonly budgetPlannerSvc: BudgetPlannerService,
              private readonly dialog: MatDialog) {}

  ngOnInit() {
    this.budgetPlannerSvc.focusedBudget$.subscribe(b => {
      console.log("budget update: ", b)
      this.focusedBudget = b
    })
    this.budgetPlannerSvc.focusedAccount$.subscribe(a => this.focusedAccount = a)
    this.apiSvc.findBudgets().subscribe(xs => this.budgets = xs)
  }

  addPaymentDialog(): void {
    //bind btn is disabled as long as this.focusedBudget is undefined
    const dialogRef = this.dialog.open(EditPaymentDialogComponent, {
      width: '80%',
      data: { accounts: this.focusedBudget.accounts, focusedAccount: this.focusedAccount }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);

    });
  }

  addBudgetDialog(): void {
    const dialogRef = this.dialog.open(EditBudgetDialogComponent, {
      width: '80%'
    });
    dialogRef.afterClosed().subscribe(result => window.location.reload())
  }

  addInvestorDialog(): void {
    const dialogRef = this.dialog.open(InvideInvestorDialogComponent, {
      width: '80%',
      data: { budget: this.focusedBudget }
    });
  }
}
