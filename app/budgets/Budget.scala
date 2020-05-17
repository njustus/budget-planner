package budgets

import common.BaseEntity
import reactivemongo.api.bson.BSONObjectID

case class Budget(name: String,
                  description: Option[String],
                  accounts: Seq[Account],
                  investors: Seq[String]=Seq.empty,
                  owner:Option[String]=None,
                  override val _id: BSONObjectID = BSONObjectID.generate())
  extends BaseEntity(_id, owner)
