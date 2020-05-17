package payments

import common.ResourceController
import javax.inject._
import play.api.Configuration
import play.api.mvc._
import security.AuthenticationService
import users.AuthUser

@Singleton
class PaymentController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                  appConfig:Configuration,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Payment](cc, appConfig, paymentCollection) {

  override def createRequestEntity(user:AuthUser, payment:Payment): Payment = payment.copy(owner=Some(user.username))
}
