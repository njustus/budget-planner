package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.Account
import persistence.tables.AccountPersistence
import play.api.mvc.ControllerComponents

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  accountPersistence: AccountPersistence)
  extends ResourceController[Account](cc, accountPersistence) {

}
