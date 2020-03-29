package controllers

import persistence.models.BaseEntity
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import io.circe.syntax._
import security.AppSecurity

abstract class ResourceController[Entity <: BaseEntity : io.circe.Encoder : io.circe.Decoder](cc: ControllerComponents)
  extends AbstractController(cc)
  with AppSecurity
  with Circe
  with JSONSerializer
  with Logging {

  implicit val ec = cc.executionContext
/*
    def findAll = withUser { user =>
      Action.async { implicit request =>
        persistence.findAll.map { xs =>
          logger.debug(s"found ${xs.knownSize} entries")
          Ok(xs.asJson)
        }
      }
    }

  def findById(id:Long) = withUser { user =>
    Action.async { implicit request =>
      logger.info(s"searching for id:$id")
      persistence.findById(id).map {
        case Some(x) =>
          logger.info(s"findById: $id returned $x")
          Ok(x.asJson)
        case None =>
          logger.info(s"findById $id NotFound")
          NotFound
      }
    }
  }

  def create = withUser { user =>
    Action.async(circe.json[Entity]) { implicit request =>
      logger.info(s"creating ${request.body}")
      persistence.insert(request.body).map { x =>
        logger.info(s"created: $x")
        Ok(x.asJson)
      }
    }
  }

  def deleteById(id: Long) = withUser { user =>
    Action.async { implicit request =>
      logger.info(s"deleting id: $id")
      persistence.deleteById(id).map(_ => NoContent)
    }
  }

 */
}