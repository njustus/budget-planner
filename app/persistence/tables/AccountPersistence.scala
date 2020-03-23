package persistence.tables

import javax.inject.Inject
import persistence.models.Account

import scala.concurrent.{ExecutionContext, Future}

class AccountPersistence @Inject()(context: QuillContext,
                        implicit val ec: ExecutionContext)
  extends Persistence[Account] {
  import context._

  private val accounts = quote(querySchema[Account]("account"))

  override def findAll: Future[List[Account]] = Future { context.run(accounts) }

  override def findById(id:Long): Future[Option[Account]] = Future { context.run(accounts.filter(p => p._id == lift(id)).take(1)).headOption }

  override def deleteById(id:Long): Future[Unit] = Future { context.run(accounts.filter(p => p._id == lift(id)).delete) }

  override def insert(a:Account): Future[Account] = Future {
    val id = context.run(accounts.insert(lift(a)).returningGenerated(_._id))
    a.copy(_id = id)
  }
}
