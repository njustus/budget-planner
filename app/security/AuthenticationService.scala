package security

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import io.circe.generic.JsonCodec
import javax.inject.Inject
import play.api.{Configuration, Logging}
import io.circe.parser
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim, JwtHeader, JwtOptions}
import users.AuthUser

import scala.util.Try

@JsonCodec
case class AuthTokenPayload(data: AuthUser, iat:Long, exp:Long)

class AuthenticationService @Inject()(config:Configuration) extends Logging {
  private val algorithm = Seq(JwtAlgorithm.RS256)
  private val secret = {
    val path = Paths.get(config.get[String]("authentication.public-key"))
    logger.debug(s"reading public key from $path")
    new String(Files.readAllBytes(path), StandardCharsets.UTF_8)
  }

  def decodeToken(token: String): Try[AuthUser] = {
    for {
      (_, jwt, _) <- JwtCirce.decodeJsonAll(token, secret, algorithm)
      _ = logger.debug(s"token decoded to $jwt")
      payload <- jwt.as[AuthTokenPayload].toTry
    } yield {
      logger.debug(s"found user $payload")
      payload.data
    }
  }
}
