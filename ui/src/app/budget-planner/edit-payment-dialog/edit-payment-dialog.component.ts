import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {Account, Payment} from '../../models'

@Component({
  selector: 'app-edit-payment-dialog',
  templateUrl: './edit-payment-dialog.component.html',
  styleUrls: ['./edit-payment-dialog.component.scss']
})
export class EditPaymentDialogComponent implements OnInit {

  paymentForm: FormGroup;

  accounts: Account[] = [
    {name:"cb", _id: "1", totalAmount:0},
    {name:"db", _id: "1", totalAmount:0},
    {name:"postbank", _id: "1", totalAmount:0},
  ]
  selectedAccount: Account = this.accounts[1]

  constructor() {
    this.paymentForm  = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl(''),
      amount: new FormControl('', [Validators.required]),
      date: new FormControl(new Date(), [Validators.required]),
      _accountId: new FormControl(this.selectedAccount._id, [Validators.required]),
    })
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.paymentForm.value)
  }
}
