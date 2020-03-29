package persistence.models

import io.circe.generic.JsonCodec
import reactivemongo.api.bson._

case class Payment(name: String,
                   description:Option[String],
                   amount: Double,
                   _accountId: BSONObjectID,
                  override val _id: BSONObjectID=BSONObjectID.generate())
  extends BaseEntity(_id)
