package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.Account
import persistence.tables.AccountPersistence
import play.api.mvc.ControllerComponents
import security.AuthenticationService

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  accountPersistence: AccountPersistence,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Account](cc, accountPersistence) {

}
