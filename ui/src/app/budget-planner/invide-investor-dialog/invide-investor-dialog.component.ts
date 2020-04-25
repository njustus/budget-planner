import { Component, OnInit, Inject } from '@angular/core';
import { DefaultService, AuthUser, Budget } from 'generated-src';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { forkJoin } from 'rxjs';

interface InvideInvestorParams {
  budget: Budget
}

@Component({
  selector: 'app-invide-investor-dialog',
  templateUrl: './invide-investor-dialog.component.html',
  styleUrls: ['./invide-investor-dialog.component.scss']
})
export class InvideInvestorDialogComponent implements OnInit {

  public investorForm: FormGroup
  public users: AuthUser[]

  constructor(
    private readonly dialogRef: MatDialogRef<InvideInvestorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: InvideInvestorParams,
    private readonly apiSvc: DefaultService) {
    this.investorForm = new FormGroup({
      usernames: new FormControl('', [Validators.required])
    })
  }

  ngOnInit() {
    this.apiSvc.findUsers().subscribe(users => {
      this.users = users
      this.investorForm.get('usernames').setValue(this.data.budget.investors)
    })
  }

  onSubmit() {
    const users = this.investorForm.value.usernames
    this.apiSvc.updateInvestors(this.data.budget._id, users).subscribe(() => this.dialogRef.close())
  }
}
