import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BudgetService } from '../budget.service';
import { Budget, Account } from 'src/app/models';
import { flatMap, map } from 'rxjs/operators';
import { BudgetPlannerService } from '../budget-planner.service';
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.scss']
})
export class BudgetComponent implements OnInit {

  public static readonly syntheticAllAccount: Account = { name: 'Total', _id: '-total-', totalAmount: 0.0 }

  public budget?: Budget

  constructor(private readonly route: ActivatedRoute,
              private readonly budgetPlannerSvc: BudgetPlannerService,
              private readonly budgetSvc: BudgetService) {}

  ngOnInit() {
    this.route.paramMap.pipe(
      map(params => params.get('budgetId')),
      flatMap(budgetId => this.budgetSvc.findById(budgetId))
    ).subscribe(b => {
      this.budget = b
      this.budgetPlannerSvc.updateFocusedBudget(b)
      this.budgetPlannerSvc.updateFocusedAccount(b.accounts[0])
      console.log("budget: ", this.budget)
    })
  }

  get accounts(): Account[] {
    // return (this.budget) ? [BudgetComponent.syntheticAllAccount, ...this.budget.accounts] : [];
    return (this.budget) ? this.budget.accounts : []
  }

  onTabChange(event: MatTabChangeEvent) {
    const focusedAccount = this.budget.accounts[event.index]
    console.log("focused account: ", focusedAccount)
    this.budgetPlannerSvc.updateFocusedAccount(focusedAccount)
  }
}
