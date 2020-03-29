package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.Account
import JSONSerializer._
import play.api.mvc.ControllerComponents
import security.AuthenticationService
//
//@Singleton
//class AccountController @Inject()(cc: ControllerComponents,
//                                 override val authenticationService: AuthenticationService)
//  extends ResourceController[Account](cc) {
//
//}
