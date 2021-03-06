import { Component, OnInit } from '@angular/core';
import { AuthenticationService, AuthenticationProvider } from '../authentication.service';
import { Subject } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {

  public providers: AuthenticationProvider[] = []
  public devUsers: string[] = []
  public isLoading$ = new Subject<boolean>()

  public selectedUser?:string

  constructor(
    private readonly cookieService: CookieService,
    private readonly authService: AuthenticationService,
    private readonly router: Router) {

      if(authService.isLoggedIn()) {
        router.navigateByUrl('/budget-planner')
      }
  }


  ngOnInit() {
    this.isLoading$.next(true)

    this.authService.findProviders().subscribe(providers => this.providers = providers)
    this.authService.findDeveloperLogin().subscribe(usernames => {
      this.devUsers=usernames
      this.selectedUser = (this.devUsers.length>0) ? this.devUsers[0]: undefined
    })
  }
}
