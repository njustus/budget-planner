package persistence.models

import io.circe.generic.JsonCodec
import reactivemongo.api.bson.BSONObjectID

case class Account(name: String,
                  override val _id: Option[BSONObjectID] = Some(BSONObjectID.generate()))
  extends BaseEntity(_id)
