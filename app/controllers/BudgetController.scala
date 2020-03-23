package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.{Budget, Payment}
import persistence.tables.BudgetPersistence
import play.api.mvc.ControllerComponents
import security.AuthenticationService

@Singleton
class BudgetController @Inject()(cc: ControllerComponents,
                                 payP: BudgetPersistence,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Budget](cc, payP) {

}
