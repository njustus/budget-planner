package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.{Budget, Payment}
import persistence.tables.BudgetPersistence
import play.api.mvc.ControllerComponents

@Singleton
class BudgetController @Inject()(cc: ControllerComponents,
                                 payP: BudgetPersistence)
  extends ResourceController[Budget](cc, payP) {

}
