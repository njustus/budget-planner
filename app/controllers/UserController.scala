package controllers

import javax.inject.{Inject, Singleton}
import persistence.collections.UserCollection
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import security.{AppSecurity, AuthenticationService}
import io.circe.syntax._

@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               userCollection: UserCollection,
                              override val authenticationService: AuthenticationService)
extends AbstractController(cc)
  with AppSecurity
  with Circe
  with JSONSerializer
  with Logging {

  implicit val ec = cc.executionContext

  def findAll = withUser { _ =>
    Action.async { implicit request =>
      userCollection.findAll().map { xs =>
        logger.debug(s"found ${xs.knownSize} entries")
        Ok(xs.asJson)
      }
    }
  }
}
