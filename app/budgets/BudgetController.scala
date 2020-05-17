package budgets

import common.{ResourceController, bpconfig}
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.mvc.ControllerComponents
import security.AuthenticationService
import users.AuthUser

@Singleton
class BudgetController @Inject()(cc: ControllerComponents,
                                 budgetCollection: BudgetCollection,
                                  config: bpconfig.BPConfig,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Budget](cc, config, budgetCollection) {

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
