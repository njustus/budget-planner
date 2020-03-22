package controllers

import javax.inject._
import persistence.tables._
import play.api.libs.circe.Circe
import play.api.mvc._
import io.circe.syntax._
import persistence.models.Payment
import play.api.Logging

import scala.concurrent.Future

@Singleton
class PaymentController @Inject()(val cc: ControllerComponents,
                                 payP: PaymentPersistence)
  extends AbstractController(cc)
  with Circe
  with Logging {

  implicit val ec = cc.executionContext

  def findAll: Action[AnyContent] = Action.async { implicit request =>
    payP.findAll.map { xs =>
        logger.debug(s"found ${xs.knownSize} entries")
        Ok(xs.asJson)
    }
  }

  def findById(id:Long): Action[AnyContent] = Action.async { implicit request =>
    logger.info(s"searching for id:$id")
    payP.findById(id).map {
      case Some(x) =>
        logger.info(s"findById: $id returned $x")
        Ok(x.asJson)
      case None =>
        logger.info(s"findById $id NotFound")
        NotFound
    }
  }

  def create: Action[Payment] = Action.async(circe.json[Payment]) { implicit request =>
    logger.info(s"creating ${request.body}")
    payP.insert(request.body).map { x =>
        logger.info(s"created: $x")
        Ok(x.asJson)
    }
  }

  def deleteById(id: Long): Action[AnyContent] = Action.async { implicit request =>
    logger.info(s"deleting id: $id")
    payP.deleteById(id).map(_ => NoContent)
  }

  def test = Action.async { implicit request =>
    Future.sequence(Seed.genPayments(1).map(payP.insert))
      .map(xs => Ok(xs.asJson))
  }
}
