package persistence.models

import io.circe.generic.JsonCodec

@JsonCodec
case class Payment(name: String,
                   description:Option[String],
                   amount: Double,
                   _accountId:Long,
                  override val _id: Long)
  extends BaseEntity(_id)
