package controllers

import javax.inject.{Inject, Singleton}
import persistence.models.Account
import JSONSerializer._
import persistence.collections.PaymentCollection
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.api.bson.BSONObjectID
import security.{AppSecurity, AuthenticationService}
import io.circe.syntax._

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                  override val authenticationService: AuthenticationService)
  extends AbstractController(cc)
  with AppSecurity
  with Circe
  with JSONSerializer
  with Logging {

  implicit val exec = cc.executionContext

  def findPayments(accountId: String) = withUser { user =>
    Action.async { req =>
      paymentCollection.findByAccount(BSONObjectID.parse(accountId).get).map(xs => Ok(xs.asJson))
    }
  }
}
