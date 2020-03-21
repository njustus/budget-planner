package persistence.tables

import play.api.Logging
import slick.jdbc.{JdbcProfile, PostgresProfile}

trait Persistence extends JdbcProfile {
  def ddls: Iterable[String]
}

object Persistence extends Persistence with Logging {
  override def ddls: Iterable[String] = BudgetPersistence.ddls ++ AccountPersistence.ddls ++ PaymentPersistence.ddls

  def ddl: String = ddls.mkString(";\n\n")
}
