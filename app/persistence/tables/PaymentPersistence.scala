package persistence.tables

import javax.inject.Inject
import persistence.models.Payment

import scala.concurrent.{ExecutionContext, Future}

class PaymentPersistence @Inject()(context: QuillContext,
                        implicit val ec: ExecutionContext) {
  import context._

  private val accounts = quote(querySchema[Payment]("payment"))

  def findAll: Future[List[Payment]] = Future { context.run(accounts) }

  def insert(p:Payment): Future[Payment] = Future {
    val id = context.run(accounts.insert(lift(p)).returningGenerated(_._id))
    p.copy(_id=id)
  }
}
