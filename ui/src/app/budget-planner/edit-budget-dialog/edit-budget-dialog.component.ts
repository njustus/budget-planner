import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { DefaultService } from 'generated-src';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-edit-budget-dialog',
  templateUrl: './edit-budget-dialog.component.html',
  styleUrls: ['./edit-budget-dialog.component.scss']
})
export class EditBudgetDialogComponent implements OnInit {

  budgetForm: FormGroup;
  accounts: FormArray;

  constructor(
    private readonly dialogRef: MatDialogRef<EditBudgetDialogComponent>,
    private readonly apiSvc: DefaultService) {
    this.accounts = new FormArray([], [Validators.minLength(1)])
    this.budgetForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      description: new FormControl(''),
      accounts: this.accounts
    })
  }

  ngOnInit() {
  }


  addAccount() {
    this.accounts.push(new FormControl(''))
  }

  removeAccount(idx: number) {
    this.accounts.removeAt(idx)
  }

  onSubmit() {
    const budget = {
      ...this.budgetForm.value,
      accounts: this.budgetForm.value.accounts.map(name => ({name}))
    }
    this.apiSvc.createBudget(budget).subscribe(b => {
      console.log("budget created: ", b)
      this.dialogRef.close()
    })
  }

}
