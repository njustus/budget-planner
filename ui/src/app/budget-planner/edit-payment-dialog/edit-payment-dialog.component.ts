import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {Account, Payment} from '../../models'
import { MAT_DIALOG_DATA } from '@angular/material';

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

  constructor(@Inject(MAT_DIALOG_DATA) public readonly data: PaymentDialogParams) {
    const todayStr = new Date().toISOString().substring(0,10)
    this.paymentForm  = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl(''),
      amount: new FormControl('', [Validators.required]),
      date: new FormControl(todayStr, [Validators.required]),
      _accountId: new FormControl(data.focusedAccount ? data.focusedAccount._id : undefined, [Validators.required]),
    })
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.paymentForm.value)
  }
}
