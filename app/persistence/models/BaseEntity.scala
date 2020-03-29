package persistence.models

import reactivemongo.api.bson._

abstract class BaseEntity(val _id: BSONObjectID) extends Product
