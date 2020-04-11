import { Component, OnInit } from '@angular/core';
import {Budget, Account} from '../models'
import { BudgetService } from './budget.service';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { EditPaymentDialogComponent } from './edit-payment-dialog/edit-payment-dialog.component';
import { BudgetPlannerService } from './budget-planner.service';

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

  constructor(private readonly budgetSvc: BudgetService,
              private readonly budgetPlannerSvc: BudgetPlannerService,
              private readonly dialog: MatDialog) { }

  ngOnInit() {
    this.budgetPlannerSvc.focusedBudget$.subscribe(b => this.focusedBudget = b)
    this.budgetPlannerSvc.focusedAccount$.subscribe(a => this.focusedAccount = a)
    this.budgetSvc.findAll().subscribe(xs => this.budgets = xs)
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

}
