package budgets

import common.BaseEntity
import reactivemongo.api.bson.BSONObjectID

case class Account(name: String,
                   totalAmount: Double = 0.0,
                  override val _id: BSONObjectID = BSONObjectID.generate())
  extends BaseEntity(_id, None)
