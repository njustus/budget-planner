import { Component, OnInit } from '@angular/core';
import { DefaultService, Budget } from 'generated-src';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public budgets: Budget[]

  constructor(private readonly apiSvc: DefaultService) { }

  ngOnInit() {
    this.apiSvc.findBudgets().subscribe(xs => this.budgets = xs)
  }

}
