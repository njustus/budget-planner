import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Budget, Account } from '../models';
import { filter, share } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BudgetPlannerService {

  private readonly _focusedBudget$ = new BehaviorSubject<Budget|undefined>(undefined);
  private readonly _focusedAccount$ = new BehaviorSubject<Account|undefined>(undefined);
  readonly focusedBudget$: Observable<Budget|undefined> = this._focusedBudget$.pipe(share())
  readonly focusedAccount$: Observable<Account> = this._focusedAccount$.pipe(filter(a => !!a), share())

  constructor() { }

  updateFocusedBudget(b:Budget) {
    this._focusedBudget$.next(b)
  }

  updateFocusedAccount(b: Account) {
    this._focusedAccount$.next(b)
  }
}
