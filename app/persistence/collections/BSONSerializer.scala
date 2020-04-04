package persistence.collections

import reactivemongo.api.bson.{BSONDocument, BSONDocumentHandler, Macros}
import persistence.models._

trait BSONSerializer {
  implicit val paymentHandler: BSONDocumentHandler[Payment] = Macros.handler[Payment]

  implicit val accountHandler: BSONDocumentHandler[Account] = Macros.handler[Account]

  implicit val budgetHandler: BSONDocumentHandler[Budget] = Macros.handler[Budget]
}

object BSONSerializer extends BSONSerializer {
  val ORDER_DESC: Int = -1
  val ORDER_ASC: Int = 1

  def orderByDESC(fieldName:String): BSONDocument = BSONDocument(fieldName -> ORDER_DESC)
  def orderByASC(fieldName:String): BSONDocument = BSONDocument(fieldName -> ORDER_ASC)
}
