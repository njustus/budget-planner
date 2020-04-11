import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {Account, Payment} from '../../models'
import { MAT_DIALOG_DATA } from '@angular/material';
import { DefaultService } from 'generated-src';

interface PaymentDialogParams {
  accounts: Account[]
  focusedAccount?: Account
}

@Component({
  selector: 'app-edit-payment-dialog',
  templateUrl: './edit-payment-dialog.component.html',
  styleUrls: ['./edit-payment-dialog.component.scss']
})
export class EditPaymentDialogComponent implements OnInit {

  paymentForm: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public readonly data: PaymentDialogParams,
              private readonly apiSvc: DefaultService) {
    const todayStr = new Date().toISOString().substring(0,10)
    this.paymentForm  = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl(''),
      amount: new FormControl(0.0, [Validators.required]),
      date: new FormControl(todayStr, [Validators.required]),
      _accountId: new FormControl(data.focusedAccount ? data.focusedAccount._id : undefined, [Validators.required]),
    })
  }

  ngOnInit() {
  }

  onSubmit() {
    const input = this.paymentForm.value;
    const payment = { ...input, amount: parseFloat(input.amount) }
    this.apiSvc.createPayment(payment).subscribe(p => console.log("payment created: ", p))
  }
}
