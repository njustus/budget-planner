package controllers

import javax.inject._
import persistence.tables._
import play.api.libs.circe.Circe
import play.api.mvc._
import io.circe.syntax._
import play.api.Logging

import scala.concurrent.Future
//import io.circe.generic.auto._

@Singleton
class BudgetController @Inject()(val cc: ControllerComponents,
                                 accP: AccountPersistence,
                                 payP: PaymentPersistence)
  extends AbstractController(cc)
  with Circe
  with Logging {
  implicit val ec = cc.executionContext

  def index = Action.async { implicit request =>
    accP.findAll.map(xs => Ok(xs.asJson))
  }

  def test = Action.async { implicit request =>
    Future.sequence(
      Seed.genPayments(1).map { p =>
        println(s"inserting $p")
        payP.insert(p)
      }
    ).map(xs => Ok(xs.asJson))
  }
}
