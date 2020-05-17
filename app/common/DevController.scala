package common

import java.time.{LocalDate, ZoneId}
import java.util.concurrent.TimeUnit

import budgets.{Account, Budget, BudgetCollection}
import com.github.javafaker.Faker
import io.circe.syntax._
import javax.inject.{Inject, Singleton}
import payments.{Payment, PaymentCollection}
import budgets._
import payments._
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.api.bson.BSONObjectID

import scala.concurrent.Future
@Singleton
class DevController @Inject()(cc: ControllerComponents,
                                 budgetCollection: BudgetCollection,
                                 paymentCollection: PaymentCollection)
  extends AbstractController(cc)
  with Circe
  with BaseJsonSupport
  with Logging {

  implicit val exec = cc.executionContext

  val faker = new Faker()
  val username = "test"

  def seed = Action.async { req =>
    val messages = (0 until 3).map { _ =>
      for {
        budget <- budget()
        payments <- Future.sequence(budget.accounts.map(a => payments(a._id)))
      } yield s"in $budget generated: ${payments.flatten.size} payments"
    }

    Future.sequence(messages).map(str => Ok(str.asJson))
  }

  def budget(): Future[Budget] = {
    val accounts = Seq(
      Account(faker.zelda().character()),
      Account(faker.zelda().character()),
    )
    val b = Budget(
      faker.internet().domainName(),
      Some(faker.hitchhikersGuideToTheGalaxy().quote()),
      accounts,
      Seq(username),
      Some(username)
    )

    budgetCollection.create(b)
  }

  def payments(accountId: BSONObjectID): Future[Seq[Payment]] = {
    Future.sequence(
        (0 until faker.number().numberBetween(0, 100)).map { _ =>
        val payment = _root_.payments.Payment(
          faker.commerce().productName(),
          Some(faker.harryPotter().quote()),
          faker.number().randomDouble(2, -600, 1000),
          LocalDate.ofInstant(faker.date().past(365, TimeUnit.DAYS).toInstant, ZoneId.systemDefault()),
          Some(username),
          accountId
        )
        paymentCollection.create(payment)
      }
    )
  }
}
