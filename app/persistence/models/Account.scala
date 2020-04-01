package persistence.models

import io.circe.generic.extras.ConfiguredJsonCodec
import controllers.JSONSerializer._
import reactivemongo.api.bson.BSONObjectID

@ConfiguredJsonCodec
case class Account(name: String,
                   totalAmount: Double = 0.0,
                  override val _id: BSONObjectID = BSONObjectID.generate())
  extends BaseEntity(_id, None)
