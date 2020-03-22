package persistence.tables

import javax.inject.Inject
import persistence.models.Account

import scala.concurrent.{ExecutionContext, Future}

class AccountPersistence @Inject()(context: QuillContext,
                        implicit val ec: ExecutionContext) {
  import context._

  private val accounts = quote(querySchema[Account]("account"))

  def findAll: Future[List[Account]] = Future { context.run(accounts) }

  def insert(a:Account): Future[Account] = Future {
    val id = context.run(accounts.insert(lift(a)).returningGenerated(_._id))
    a.copy(_id = id)
  }
}
