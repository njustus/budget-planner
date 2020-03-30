import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BudgetService } from '../budget.service';
import { Budget } from 'src/app/models';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.scss']
})
export class BudgetComponent implements OnInit {

  private readonly budgetId: string
  public budget?: Budget

  constructor(private readonly route: ActivatedRoute,
              private readonly budgetSvc: BudgetService) {
    this.budgetId = route.snapshot.paramMap.get('budgetId')
  }

  ngOnInit() {
    this.budgetSvc.findById(this.budgetId).subscribe(b => {
      this.budget = b
      console.log("budget: ", this.budget)
    })
  }

}