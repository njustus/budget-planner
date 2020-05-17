package common

import reactivemongo.api.bson.BSONDocument

trait BaseBSONHandler {
}

object BaseBSONHandler {
  val ORDER_DESC: Int = -1
  val ORDER_ASC: Int = 1

  def orderByDESC(fieldName:String): BSONDocument = BSONDocument(fieldName -> ORDER_DESC)
  def orderByASC(fieldName:String): BSONDocument = BSONDocument(fieldName -> ORDER_ASC)
}
