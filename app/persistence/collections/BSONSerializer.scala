package persistence.collections

import reactivemongo.api.bson.{BSONDocumentHandler, Macros}
import persistence.models._

trait BSONSerializer {
  implicit val paymentHandler: BSONDocumentHandler[Payment] = Macros.handler[Payment]

  implicit val accountHandler: BSONDocumentHandler[Account] = Macros.handler[Account]

  implicit val budgetHandler: BSONDocumentHandler[Budget] = Macros.handler[Budget]
}

object BSONSerializer extends BSONSerializer
