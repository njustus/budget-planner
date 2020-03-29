package controllers

import javax.inject._
import play.api.mvc._
import io.circe.syntax._
import persistence.models.Payment
import JSONSerializer._
import persistence.collections.PaymentCollection
import security.AuthenticationService

import scala.concurrent.Future

@Singleton
class PaymentController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Payment](cc, paymentCollection) {

}
