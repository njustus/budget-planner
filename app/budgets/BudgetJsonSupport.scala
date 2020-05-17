package budgets

import common.JSONSerializer
import io.circe._
import io.circe.generic.extras.semiauto._
import reactivemongo.api.bson.BSONObjectID

trait BudgetJsonSupport extends JSONSerializer {
  // =========== override generated decoders to support lazily-generated BSONObjectIDs per entity

  implicit val accountEncoder: Encoder[Account] = deriveConfiguredEncoder[Account]
  implicit val accountDecoder: Decoder[Account] = deriveConfiguredDecoder[Account].map { a =>
    a.copy(_id=BSONObjectID.generate())
  }

  implicit val budgetEncoder: Encoder[Budget] = deriveConfiguredEncoder[Budget]
  implicit val budgetDecoder: Decoder[Budget] = deriveConfiguredDecoder[Budget].map { b =>
    b.copy(_id=BSONObjectID.generate())
  }
}
