package persistence.tables

import javax.inject.Inject
import persistence.models.Budget

import scala.concurrent.{ExecutionContext, Future}

class BudgetPersistence @Inject()(context: QuillContext,
                        implicit val ec: ExecutionContext)
  extends Persistence[Budget] {
  import context._

  private val budgets = quote(querySchema[Budget]("budget"))

  override def findAll: Future[List[Budget]] = Future { context.run(budgets) }

  override def findById(id: Long): Future[Option[Budget]] = Future { context.run(budgets.filter(p => p._id == lift(id)).take(1)).headOption }

  override def deleteById(id: Long): Future[Unit] = Future { context.run(budgets.filter(p => p._id == lift(id)).delete) }

  override def insert(a: Budget): Future[Budget] = Future {
    val id = context.run(budgets.insert(lift(a)).returningGenerated(_._id))
    a.copy(_id = id)
  }
}
