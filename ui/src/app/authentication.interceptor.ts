import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private readonly authSvc: AuthenticationService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.urlWithParams.indexOf("budget-planner") != -1) {
      const reqNew = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.authSvc.currentToken}`
        }
      })
      return next.handle(reqNew)
    } else {
      return next.handle(req)
    }
  }

}
