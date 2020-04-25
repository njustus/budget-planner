import { Component, OnInit, Input } from '@angular/core';
import {Account, Payment, Budget} from '../../models'
import { formatCurrency } from '@angular/common';
import { DefaultService } from 'generated-src';
import { MatDialog } from '@angular/material';
import { EditPaymentDialogComponent } from '../edit-payment-dialog/edit-payment-dialog.component';
import {PageEvent} from "@angular/material/paginator";
import {HttpHeaders} from "@angular/common/http";

const RANGE_HEADER = "Content-Range";

interface PageData {
  pageNumber: number
  pageSize: number
  total: number
}

function parseRangeHeader(pageNumber:number, headers: HttpHeaders): PageData {
  const value = headers.get(RANGE_HEADER)
  console.log("header: ", value)
  const [_, start, end, total] = /(\d+)\s*--\s*(\d+)\s*\/\s*(\d+)/g.exec(value)
  return {
    pageNumber: pageNumber,
    pageSize: 50,
    total: Number.parseInt(total)
  }
}

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {

  @Input() public account: Account
  @Input() public budget: Budget

  public payments?: Payment[]
  public pageData?: PageData

  constructor(private readonly apiSvc: DefaultService,
    private readonly dialog: MatDialog) { }

  ngOnInit() {
    this.updatePayments()
  }

  pageChanged(ev: PageEvent) {
    this.updatePayments(ev.pageIndex+1)
  }

  editPayment(payment:Payment) {
    const dialogRef = this.dialog.open(EditPaymentDialogComponent, {
      width: '80%',
      data: { accounts: this.budget.accounts, focusedAccount: this.account, editingPayment: payment }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
    });
  }

  deletePayment(payment: Payment) {
    this.apiSvc.deletePayment(payment._id).subscribe(() => this.updatePayments())
  }

  updatePayments(page: number = 1) {
    this.payments = undefined
    this.apiSvc.findAccountPayments(this.account._id, page, 'response').subscribe(response => {
      this.pageData = parseRangeHeader(page, response.headers)
      console.log("pageData: ", this.pageData)
      this.payments = response.body
    })
  }
}
