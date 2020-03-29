package controllers

import javax.inject.{Inject, Singleton}
import persistence.collections.BudgetCollection
import persistence.models.{Account, Budget, Payment}
import play.api.mvc.ControllerComponents
import reactivemongo.bson.BSONObjectID
import security.AuthenticationService
import JSONSerializer._
import io.circe.syntax._

@Singleton
class BudgetController @Inject()(cc: ControllerComponents,
                                 budgetCollection: BudgetCollection,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Budget](cc) {

  def findAll = Action.async { implicit req =>
    val b = Budget("test-b", Some("blup"), Seq(Account("commerzbank"), Account("db")))
    budgetCollection.create(b).map(b => Ok(b.asJson))
  }
}
