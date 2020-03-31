import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payment } from '../models';
import { budgetPrefix } from './budget.service';

const accountPrefix = "/finance-manager/accounts/"

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  constructor(private readonly http: HttpClient) { }

  findPayments(accountId: string): Observable<Payment[]> {
    const url = accountPrefix + `${accountId}/payments`
    return this.http.get<Payment[]>(url)
  }
}