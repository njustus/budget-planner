import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {Account, Payment} from '../../models'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DefaultService } from 'generated-src';

interface PaymentDialogParams {
  accounts: Account[]
  focusedAccount?: Account
  editingPayment?: Payment
}

@Component({
  selector: 'app-edit-payment-dialog',
  templateUrl: './edit-payment-dialog.component.html',
  styleUrls: ['./edit-payment-dialog.component.scss']
})
export class EditPaymentDialogComponent implements OnInit {

  paymentForm: FormGroup;

  constructor(
    private readonly dialogRef: MatDialogRef<EditPaymentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: PaymentDialogParams,
              private readonly apiSvc: DefaultService) {
    const todayStr = new Date().toISOString().substring(0,10)
    this.paymentForm  = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl(''),
      amount: new FormControl(0.0, [Validators.required]),
      date: new FormControl(todayStr, [Validators.required]),
      _accountId: new FormControl(data.focusedAccount ? data.focusedAccount._id : undefined, [Validators.required]),
    })

    if(data.editingPayment) {
      delete data.editingPayment.owner
      delete data.editingPayment._id
      this.paymentForm.setValue(data.editingPayment)
    }
  }

  ngOnInit() {
  }

  onSubmit() {
    //TODO: update the original entity if editingPayment is set
    const input = this.paymentForm.value;
    const payment = { ...input, amount: parseFloat(input.amount) }
    this.apiSvc.createPayment(payment).subscribe(p => {
      console.log("payment created: ", p)
      this.dialogRef.close(p)
    })
  }
}
