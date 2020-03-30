import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Subject, BehaviorSubject, Observable } from 'rxjs';
import {map} from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

export interface AuthenticationProvider {
  color?: string
  icon?: string
  name: string
  provider: string
}

const authServiceRoute = "api/v1/auths"
const bearerCookie = 'token'

export function securityInterceptor(cookieService: CookieService, authService: AuthenticationService) {
  return () => {
      if (cookieService.check(bearerCookie)) {
        const token = cookieService.get(bearerCookie)
        console.log("found token: ", token)

        authService.updateToken(token)
      }
  }
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private token$: BehaviorSubject<string | undefined> = new BehaviorSubject(undefined)

  constructor(private readonly http: HttpClient) {
  }

  updateToken(token:string): void {
    this.token$.next(token)
  }

  get currentToken(): string {
    if(this.token$.value === undefined) {
      throw new Error(`no current Bearer token available!`);
    }

    return this.token$.value
  }

  isLoggedIn(): boolean {
    return !!this.token$.value
  }

  findDeveloperLogin(): Observable<string[]> {
    return this.http.get<{ data: string[] }>(authServiceRoute+"/developer/usernames").pipe(
      map(any => any.data)
    )
  }

  findProviders(): Observable<AuthenticationProvider[]> {
    return this.http.get<{ data: AuthenticationProvider[] }>(authServiceRoute).pipe(
      map(any => any.data)
    )
  }
}
