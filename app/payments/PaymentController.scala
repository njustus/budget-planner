package payments

import common.{ResourceController, bpconfig}
import javax.inject._
import play.api.Configuration
import play.api.mvc._
import security.AuthenticationService
import users.AuthUser

@Singleton
class PaymentController @Inject()(cc: ControllerComponents,
                                  paymentCollection: PaymentCollection,
                                  config: bpconfig.BPConfig,
                                 override val authenticationService: AuthenticationService)
  extends ResourceController[Payment](cc, config, paymentCollection) {

  override def createRequestEntity(user:AuthUser, payment:Payment): Payment = payment.copy(owner=Some(user.username))
}
