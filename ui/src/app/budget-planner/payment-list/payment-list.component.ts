import { Component, OnInit, Input } from '@angular/core';
import { AccountService } from '../account.service';
import {Account, Payment} from '../../models'

@Component({
  selector: 'app-payment-list',
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss']
})
export class PaymentListComponent implements OnInit {

  @Input() public account: Account

  public payments?: Payment[]

  constructor(private readonly accountSvc: AccountService) { }

  ngOnInit() {
    this.accountSvc.findPayments(this.account._id).subscribe(xs => this.payments = xs)
  }

}
