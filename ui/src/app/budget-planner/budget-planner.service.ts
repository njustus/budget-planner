import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Budget, Account } from '../models';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BudgetPlannerService {

  private readonly _focusedBudget$ = new BehaviorSubject<Budget|undefined>(undefined);
  private readonly _focusedAccount$ = new BehaviorSubject<Account|undefined>(undefined);

  constructor() { }

  updateFocusedBudget(b:Budget) {
    this._focusedBudget$.next(b)
  }

  updateFocusedAccount(b: Account) {
    this._focusedAccount$.next(b)
  }

  get focusedBudget$(): Observable<Budget> {
    return this._focusedBudget$.pipe(
      filter(x => !!x)
    )
  }

  get focusedAccount$(): Observable<Account> {
    return this._focusedAccount$.pipe(
      filter(x => !!x)
    )
  }
}
