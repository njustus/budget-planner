package persistence.models

import io.circe.generic.JsonCodec

@JsonCodec
case class Account(name: String,
                  _budgetId: Long,
                  override val _id: Long)
  extends BaseEntity(_id)
