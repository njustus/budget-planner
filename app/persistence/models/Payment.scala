package persistence.models

import java.time.LocalDate

import reactivemongo.api.bson.BSONObjectID

case class Payment(name: String,
                   description:Option[String],
                   amount: Double,
                   date: LocalDate,
                   owner:Option[String]=None,
                   _accountId: BSONObjectID,
                   override val _id: BSONObjectID=BSONObjectID.generate())
  extends BaseEntity(_id, owner)
