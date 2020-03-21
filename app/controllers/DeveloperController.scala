package controllers

import javax.inject._
import persistence.tables.Seed
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.circe.Circe
import play.api.mvc._
import slick.jdbc.JdbcProfile
import io.circe.syntax._
import io.circe.generic.auto._

@Singleton
class DeveloperController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                                     val cc: ControllerComponents)
  extends AbstractController(cc)
  with Circe
  with HasDatabaseConfigProvider[JdbcProfile] {
  implicit val context = cc.executionContext

  def seedDatabase = Action.async { implicit request: Request[AnyContent] =>
    for {
      payments <- dbConfigProvider.get.db.run(Seed.seedDatabase)
    } yield Ok(payments.asJson)
  }
}
