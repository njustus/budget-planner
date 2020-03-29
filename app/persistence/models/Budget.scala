package persistence.models

import io.circe.generic.extras.ConfiguredJsonCodec
import controllers.JSONSerializer._
import reactivemongo.api.bson._

@ConfiguredJsonCodec
case class Budget(name: String,
                  description: Option[String],
                  accounts: Seq[Account],
                  investors: Seq[String]=Seq.empty,
                  owner:Option[String]=None,
                  override val _id: BSONObjectID = BSONObjectID.generate())
  extends BaseEntity(_id, owner)
