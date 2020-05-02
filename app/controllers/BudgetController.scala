package controllers

import javax.inject.{Inject, Singleton}
import persistence.collections.BudgetCollection
import persistence.models.{Account, AuthUser, Budget, Payment}
import play.api.mvc.{ControllerComponents, EssentialAction}
import security.AuthenticationService
import io.circe.syntax._
import play.api.Configuration
import JSONSerializer._

@Singleton
class BudgetController @Inject()(cc: ControllerComponents,
                                 budgetCollection: BudgetCollection,
                                appConfig: Configuration,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Budget](cc, appConfig, budgetCollection) {

  override def createRequestEntity(user:AuthUser, budget:Budget): Budget = budget.copy(owner=Some(user.username), investors = Seq(user.username))

  override def findAll(page: Option[Int]) = withUser { user =>
    Action.async { implicit request =>
      budgetCollection.findByInvestor(user.username, paginate(page)).map { paginated =>
        logger.debug(s"found  ${paginated.totalCount} entries; returning page: ${paginated.pageNumber.getOrElse(-1)}")
        paginated.rangeString match {
          case Some(range) => Ok(paginated.data.asJson).withHeaders(CONTENT_RANGE -> range)
          case None => Ok(paginated.data.asJson)
        }
      }
    }
  }

  def updateInvestors(budgetId: String) = withUser { user =>
    Action.async(circe.json[Seq[String]]) { implicit request =>
      budgetCollection.updateInvestors(budgetId, request.body).map {
        case Some(b) => Ok(b.asJson)
        case None => NotFound
      }
    }
  }
}
