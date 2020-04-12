package controllers

import io.circe.generic.JsonCodec
import io.circe.generic.extras.ConfiguredJsonCodec
import javax.inject.{Inject, Singleton}
import persistence.collections.BudgetCollection
import persistence.models.{Account, AuthUser, Budget, Investor, Payment}
import play.api.mvc.ControllerComponents
import security.AuthenticationService
import io.circe.syntax._

@Singleton
class BudgetController @Inject()(cc: ControllerComponents,
                                 budgetCollection: BudgetCollection,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Budget](cc, budgetCollection) {

  override def createRequestEntity(user:AuthUser, budget:Budget): Budget = budget.copy(owner=Some(user.username), investors = Seq(user.username))

  def addInvestor(budgetId: String) = withUser { user =>
    Action.async(circe.json[Investor]) { implicit request =>
      budgetCollection.addInvestor(budgetId, request.body.username).map {
        case Some(b) => Ok(b.asJson)
        case None => NotFound
      }
    }
  }
}
