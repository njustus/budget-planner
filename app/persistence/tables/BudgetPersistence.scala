package persistence.tables

import persistence.models.Budget

object BudgetPersistence extends Persistence {
  import api._

  class BudgetTable(tag: Tag) extends Table[Budget](tag, "budget") {
    def id = column[Long]("_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def description = column[Option[String]]("description")

    override def * = (name, description, id) <> (Budget.tupled, Budget.unapply)
  }

  private[tables] val tableQuery = TableQuery[BudgetTable]

  override def ddls: Iterable[String] = tableQuery.schema.create.statements

  def create(budget: Budget) = {
    val query = tableQuery returning tableQuery.map(_.id) into ((budget, id) => budget.copy(_id=id))
    query += budget
  }
}
