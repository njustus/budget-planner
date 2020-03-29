package persistence.models

import reactivemongo.api.bson._

abstract class BaseEntity(val _id: Option[BSONObjectID]) extends Product
