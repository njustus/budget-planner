package persistence.tables

import persistence.models.Payment

object PaymentPersistence extends Persistence {
  import api._

  class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {
    def id = column[Long]("_id", O.PrimaryKey, O.AutoInc)
    def account_fk = column[Long]("_account_id")

    def name = column[String]("name")
    def description = column[Option[String]]("description")
    def amount = column[Double]("amount")

    def account = foreignKey("account_fk", account_fk, AccountPersistence.tableQuery)(_.id)

    override def * = (name, description, amount, account_fk, id) <> (Payment.tupled, Payment.unapply)
  }

  private[tables] val tableQuery = TableQuery[PaymentTable]

  override def ddls: Iterable[String] = tableQuery.schema.create.statements

  def insert(payment: Payment) = {
    val query = tableQuery returning tableQuery.map(_.id) into ((payment, id) => payment.copy(_id=id))
    query += payment
  }
}
