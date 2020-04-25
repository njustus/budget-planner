import { Component, OnInit, Input } from '@angular/core';
import {Account, Payment, Budget} from '../../models'
import { formatCurrency } from '@angular/common';
import { DefaultService } from 'generated-src';
import { MatDialog } from '@angular/material';
import { EditPaymentDialogComponent } from '../edit-payment-dialog/edit-payment-dialog.component';
import {PageEvent} from "@angular/material/paginator";

const rangeRegex = /\d+\s*--\s*\d+\s*\/\s*\d+/gm;

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {

  @Input() public account: Account
  @Input() public budget: Budget

  public payments?: Payment[]

  constructor(private readonly apiSvc: DefaultService,
    private readonly dialog: MatDialog) { }

  ngOnInit() {
    this.updatePayments()
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

  updatePayments() {
    this.payments = undefined
    this.apiSvc.findAccountPayments(this.account._id).subscribe(xs => this.payments = xs)
  }
}
