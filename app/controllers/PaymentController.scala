package controllers

import javax.inject._
import play.api.mvc._
import persistence.models.{AuthUser, Payment}
import persistence.collections.PaymentCollection
import play.api.Configuration
import security.AuthenticationService
import JSONSerializer._

@Singleton
class PaymentController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                  appConfig:Configuration,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Payment](cc, appConfig, paymentCollection) {

  override def createRequestEntity(user:AuthUser, payment:Payment): Payment = payment.copy(owner=Some(user.username))
}
