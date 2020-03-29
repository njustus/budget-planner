package persistence.models

import io.circe.generic.extras.ConfiguredJsonCodec
import controllers.JSONSerializer._
import reactivemongo.api.bson._

@ConfiguredJsonCodec
case class Payment(name: String,
                   description:Option[String],
                   amount: Double,
                   _accountId: BSONObjectID,
                  override val _id: BSONObjectID=BSONObjectID.generate())
  extends BaseEntity(_id)
