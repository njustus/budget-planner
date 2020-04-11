import { Component, OnInit, Input } from '@angular/core';
import {Account, Payment} from '../../models'
import { formatCurrency } from '@angular/common';
import { DefaultService } from 'generated-src';

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {

  @Input() public account: Account

  public payments?: Payment[]

  constructor(private readonly apiSvc: DefaultService) { }

  ngOnInit() {
    this.apiSvc.findAccountPayments(this.account._id).subscribe(xs => this.payments = xs)
  }

}
