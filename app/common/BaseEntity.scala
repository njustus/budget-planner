package common

import reactivemongo.api.bson._

abstract class BaseEntity(val _id: BSONObjectID, owner:Option[String]) extends Product
