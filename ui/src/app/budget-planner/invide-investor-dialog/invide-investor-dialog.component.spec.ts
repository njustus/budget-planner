import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvideInvestorDialogComponent } from './invide-investor-dialog.component';

describe('InvideInvestorDialogComponent', () => {
  let component: InvideInvestorDialogComponent;
  let fixture: ComponentFixture<InvideInvestorDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvideInvestorDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvideInvestorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
