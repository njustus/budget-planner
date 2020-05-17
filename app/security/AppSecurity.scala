package security

import play.api.Logging
import play.api.mvc._
import play.mvc.Http.HeaderNames
import users.AuthUser

import scala.util.matching.Regex
import scala.util.{Failure, Success}

trait AppSecurity {
  self: Logging =>

  def authenticationService: AuthenticationService

  def username(request: RequestHeader): Option[AuthUser] = {
    val tokenOpt = request.headers.get(HeaderNames.AUTHORIZATION).collect { case AppSecurity.tokenExtractor(token) => token }

    val userOpt = (token:String) => authenticationService.decodeToken(token) match {
      case Success(user) => Some(user)
      case Failure(exception) =>
        logger.warn(s"couldn't decode token; got: $exception")
        None
    }

    tokenOpt.flatMap(userOpt)
  }

  def onUnauthorized(request: RequestHeader): Result =
    Results.Unauthorized(
      s"""provide a valid jwt as authorization header!
         |${HeaderNames.AUTHORIZATION}: Bearer <jwt token>
         |""".stripMargin)

  def withUser[A](fn: AuthUser => Action[A]): EssentialAction =
    Security.Authenticated[AuthUser](username, onUnauthorized) { user =>
      logger.info(s"authenticated as $user")
      fn(user)
    }
}

object AppSecurity {
  val tokenExtractor: Regex = """Bearer\s+(.+)""".r
}
