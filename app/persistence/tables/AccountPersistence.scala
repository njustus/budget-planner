package persistence.tables

import persistence.models.Account
import slick.jdbc.JdbcProfile

object AccountPersistence extends Persistence {
  import api._

  class AccountTable(tag: Tag) extends Table[Account](tag, "account") {
    def id = column[Long]("_id", O.PrimaryKey, O.AutoInc)
    def budget_fk= column[Long]("_budgetId")
    def budget = foreignKey("budget_fk", budget_fk, BudgetPersistence.tableQuery)(_.id)

    def name = column[String]("name")

    override def * = (name, budget_fk, id) <> (Account.tupled, Account.unapply)
  }

  private[tables] val tableQuery = TableQuery[AccountTable]

  override def ddls: Iterable[String] = tableQuery.schema.create.statements

  def create(account: Account) = {
    val query = tableQuery returning tableQuery.map(_.id) into ((account, id) => account.copy(_id=id))
    query += account
  }
}
