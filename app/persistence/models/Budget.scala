package persistence.models

import io.circe.generic.JsonCodec

@JsonCodec
case class Budget(name: String,
                  description: Option[String],
                  override val _id: Long)
  extends BaseEntity(_id)
