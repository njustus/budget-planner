import { Component, OnInit } from '@angular/core';
import {Budget} from '../models'
import { BudgetService } from './budget.service';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { EditPaymentDialogComponent } from './edit-payment-dialog/edit-payment-dialog.component';

@Component({
  selector: 'app-budget-planner',
  templateUrl: './budget-planner.component.html',
  styleUrls: ['./budget-planner.component.scss']
})
export class BudgetPlannerComponent implements OnInit {

  budgets: Budget[] = []

  constructor(private readonly budgetSvc: BudgetService,
              private readonly dialog: MatDialog) { }

  ngOnInit() {
    this.budgetSvc.findAll().subscribe(xs => this.budgets = xs)
  }

  addPaymentDialog(): void {
    const dialogRef = this.dialog.open(EditPaymentDialogComponent, {
      // height: '80%',
      width: '80%',
      // data: {name: this.name, animal: this.animal}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);

    });
  }

}
