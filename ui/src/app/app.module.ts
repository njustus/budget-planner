import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { LandingComponent } from './landing/landing.component';
import { ReactiveFormsModule } from '@angular/forms';

import {CookieService} from 'ngx-cookie-service';
import { BudgetPlannerModule } from './budget-planner/budget-planner.module';
import { AuthenticationService, securityInterceptor } from './authentication.service';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared/shared.module';
import { ApiModule, BASE_PATH } from 'generated-src';

@NgModule({
  declarations: [
    AppComponent,
    LandingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CommonModule,
    SharedModule,
    BudgetPlannerModule,
    ReactiveFormsModule,
    ApiModule
  ],
  providers: [
    CookieService,
    {
      provide: APP_INITIALIZER,
      useFactory: securityInterceptor,
      multi: true,
      deps: [CookieService, AuthenticationService]
    },
    {
      provide: BASE_PATH,
      useValue: window.location.origin + "/api/v1/budget-planner",
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
