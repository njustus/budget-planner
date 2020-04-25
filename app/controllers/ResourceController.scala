package controllers

import persistence.models.{AuthUser, BaseEntity, PaginatedEntity}
import play.api.{Application, Configuration, Logging}
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, ResponseHeader, Result}
import io.circe.syntax._
import persistence.collections.CRUDCollection
import security.AppSecurity

abstract class ResourceController[Entity <: BaseEntity : io.circe.Encoder : io.circe.Decoder](cc: ControllerComponents,
                                                                                              appConfig: Configuration,
                                                                                              persistence:CRUDCollection[Entity])
  extends AbstractController(cc)
  with AppSecurity
  with Circe
  with JSONSerializer
  with Logging {

  implicit val ec = cc.executionContext

  val paginate = Paginate.curried(appConfig.get[Int]("rest.page-size"))

    def findAll(page: Option[Int]) = withUser { user =>
      Action.async { implicit request =>
        persistence.findAll(paginate(page)).map { paginated: PaginatedEntity[Entity] =>
          logger.debug(s"found  ${paginated.totalCount} entries; returning page: ${paginated.pageNumber.getOrElse(-1)}")
          paginated.rangeString match {
            case Some(range) => Ok(paginated.data.asJson).withHeaders(CONTENT_RANGE -> range)
            case None => Ok(paginated.data.asJson)
          }
        }
      }
    }

  def findById(id: String) = withUser { user =>
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
      val entity = createRequestEntity(user, request.body)
      logger.info(s"creating ${entity}")
      persistence.create(entity).map { x =>
        logger.info(s"created: $x")
        Ok(x.asJson)
      }
    }
  }

  def deleteById(id: String) = withUser { user =>
    Action.async { implicit request =>
      logger.info(s"deleting id: $id")
      persistence.deleteById(id).map(_ => NoContent)
    }
  }

  def createRequestEntity(user:AuthUser, e:Entity): Entity = e
}
