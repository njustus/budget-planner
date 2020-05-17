package budgets

import common.{BaseJsonSupport, Paginate, bpconfig}
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import payments.PaymentCollection
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.{Configuration, Logging}
import reactivemongo.api.bson.BSONObjectID
import security.{AppSecurity, AuthenticationService}

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                  config: bpconfig.BPConfig,
                                  override val authenticationService: AuthenticationService)
  extends AbstractController(cc)
  with AppSecurity
  with Circe
  with BaseJsonSupport
  with Logging {

  implicit val exec = cc.executionContext
  val paginate = Paginate.curried(config.api.pageSize)

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
