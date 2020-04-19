package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.Account
import JSONSerializer._
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
      paymentCollection.findByAccount(BSONObjectID.parse(accountId).get, paginate(page)).map(xs => Ok(xs.asJson))
    }
  }
}
