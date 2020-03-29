package persistence.collections

import reactivemongo.api.bson.Macros
import persistence.models._

trait BSONSerializer {
  implicit val paymentHandler = Macros.handler[Payment]

  implicit val accountHandler = Macros.handler[Account]

  implicit val budgetHandler = Macros.handler[Budget]
}
