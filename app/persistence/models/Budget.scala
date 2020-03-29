package persistence.models

import io.circe.generic.JsonCodec
import reactivemongo.api.bson._

case class Budget(name: String,
                  description: Option[String],
                  accounts: Seq[Account],
                  override val _id: BSONObjectID = BSONObjectID.generate())
  extends BaseEntity(_id)
