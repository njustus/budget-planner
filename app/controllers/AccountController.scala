package controllers

import javax.inject.{Inject, Singleton}
import persistence.collections.PaymentCollection
import play.api.{Configuration, Logging}
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.api.bson.BSONObjectID
import security.{AppSecurity, AuthenticationService}
import io.circe.syntax._

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                  appConfig:Configuration,
                                  override val authenticationService: AuthenticationService)
  extends AbstractController(cc)
  with AppSecurity
  with Circe
  with JSONSerializer
  with Logging {

  implicit val exec = cc.executionContext
  val paginate = Paginate.curried(appConfig.get[Int]("rest.page-size"))

  def findPayments(accountId: String, page:Option[Int]) = withUser { user =>
    Action.async { req =>
      paymentCollection.findByAccount(BSONObjectID.parse(accountId).get, paginate(page)).map { paginated =>
          logger.debug(s"found  ${paginated.totalCount} entries; returning page: ${paginated.pageNumber.getOrElse(-1)}")
          paginated.rangeString match {
            case Some(range) => Ok(paginated.data.asJson).withHeaders(CONTENT_RANGE -> range)
            case None => Ok(paginated.data.asJson)
          }
        }
    }
  }
}
