import { Component, OnInit } from '@angular/core';
import {Budget} from '../models'
import { BudgetService } from './budget.service';

@Component({
  selector: 'app-budget-planner',
  templateUrl: './budget-planner.component.html',
  styleUrls: ['./budget-planner.component.scss']
})
export class BudgetPlannerComponent implements OnInit {

  budgets: Budget[] = []

  constructor(private readonly budgetSvc: BudgetService) { }

  ngOnInit() {
    this.budgetSvc.findAll().subscribe(xs => this.budgets = xs)
  }

}
