package controllers

import javax.inject._
import play.api.mvc._
import io.circe.syntax._
import persistence.models.Payment
import JSONSerializer._
import security.AuthenticationService

import scala.concurrent.Future

@Singleton
class PaymentController @Inject()(cc: ControllerComponents,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Payment](cc) {

}
