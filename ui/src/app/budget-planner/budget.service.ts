import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Budget } from '../models';

export const budgetPrefix = "/api/v1/budget-planner/budgets"

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  constructor(private readonly http: HttpClient) { }

  findAll(): Observable<Budget[]> {
    return this.http.get<Budget[]>(budgetPrefix)
  }

  findById(id: string): Observable<Budget> {
    return this.http.get<Budget>(budgetPrefix+"/"+id)
  }
}
