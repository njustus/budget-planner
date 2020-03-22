package persistence.tables

import com.zaxxer.hikari.HikariDataSource
import io.getquill.{Escape, PostgresJdbcContext}
import javax.inject._

@Singleton
class QuillContext @Inject() (db: play.db.Database)
  extends PostgresJdbcContext(Escape, db.getDataSource.asInstanceOf[HikariDataSource]) {
}
