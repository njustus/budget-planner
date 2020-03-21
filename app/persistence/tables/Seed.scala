package persistence.tables

import com.github.javafaker.Faker
import persistence.models._
import slick.dbio.DBIOAction

import scala.concurrent.ExecutionContext
import scala.util.Random

object Seed {
  val faker = new Faker()

  def seedDatabase(implicit exec:ExecutionContext) = {
    for {
      budget <- BudgetPersistence.create(Budget(
        faker.internet().domainName(),
        Some(faker.lorem().paragraph()),
        0))
      account <- AccountPersistence.create(
        Account(faker.finance().iban(), budget._id, 0)
      )
      payments = genPayments(account._id)
      result <- DBIOAction.sequence(payments.map(PaymentPersistence.insert).toVector)
    } yield result
  }

  def genPayments(accountId: Long): Seq[Payment] = {
    for(_ <- 0 until 1000) yield Payment(
      faker.commerce().productName(),
      if(Random.nextFloat() > 0.5)
        Some(faker.hitchhikersGuideToTheGalaxy().quote())
      else None,
      faker.number().randomDouble(2, -1000, 250), accountId, 0
    )
  }
}
