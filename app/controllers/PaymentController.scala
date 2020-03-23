package controllers

import javax.inject._
import persistence.tables._
import play.api.mvc._
import io.circe.syntax._
import persistence.models.Payment

import scala.concurrent.Future

@Singleton
class PaymentController @Inject()(cc: ControllerComponents,
                                 payP: PaymentPersistence)
  extends ResourceController[Payment](cc, payP) {

  def test = Action.async { implicit request =>
    Future.sequence(Seed.genPayments(1).map(payP.insert))
      .map(xs => Ok(xs.asJson))
  }
}
