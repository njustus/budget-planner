import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BudgetService } from '../budget.service';
import { Budget, Account } from 'src/app/models';
import { flatMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.scss']
})
export class BudgetComponent implements OnInit {

  public static readonly syntheticAllAccount: Account = { name: 'Total', _id: '-total-', totalAmount: 0.0 }

  public budget?: Budget

  constructor(private readonly route: ActivatedRoute,
              private readonly budgetSvc: BudgetService) {}

  ngOnInit() {
    this.route.paramMap.pipe(
      map(params => params.get('budgetId')),
      flatMap(budgetId => this.budgetSvc.findById(budgetId))
    ).subscribe(b => {
      this.budget = b
      console.log("budget: ", this.budget)
    })
  }

  get accounts(): Account[] {
    // return (this.budget) ? [BudgetComponent.syntheticAllAccount, ...this.budget.accounts] : [];
    return (this.budget) ? this.budget.accounts : []
  }
}
