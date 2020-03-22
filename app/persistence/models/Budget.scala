package persistence.models

import io.circe.generic.JsonCodec

@JsonCodec
case class Budget(name: String,
                  description: Option[String],
                 _id: Long)
