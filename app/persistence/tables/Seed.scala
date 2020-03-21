package persistence.tables

import com.github.javafaker.Faker
import persistence.models._

import scala.concurrent.ExecutionContext

object Seed {
  val faker = new Faker()

  def seedDatabase(implicit exec:ExecutionContext) = {
    for {
      budget <- BudgetPersistence.create(Budget(
        faker.internet().domainName(),
        Some(faker.lorem().paragraph()),
        0))
      account <- AccountPersistence.create(
        Account(faker.business().creditCardNumber(), budget._id, 0)
      )
    } yield (budget, account)
  }
}
