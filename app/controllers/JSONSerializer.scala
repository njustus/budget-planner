package controllers

import reactivemongo.api.bson.BSONObjectID
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import persistence.models._

trait JSONSerializer {

  implicit val config: Configuration = Configuration.default.withDefaults

  implicit val bsonIdEncoder: Encoder[BSONObjectID] = Encoder.encodeString.contramap[BSONObjectID](objId => objId.stringify)
  implicit val bsonIdDecoder: Decoder[BSONObjectID] = Decoder.decodeString.emapTry(BSONObjectID.parse)

  // =========== override generated decoders to support lazily-generated BSONObjectIDs per entity
  implicit val accountEncoder: Encoder[Account] = deriveConfiguredEncoder[Account]
  implicit val accountDecoder: Decoder[Account] = deriveConfiguredDecoder[Account].map { a =>
    a.copy(_id=BSONObjectID.generate())
  }

  implicit val budgetEncoder: Encoder[Budget] = deriveConfiguredEncoder[Budget]
  implicit val budgetDecoder: Decoder[Budget] = deriveConfiguredDecoder[Budget].map { b =>
    b.copy(_id=BSONObjectID.generate())
  }

  implicit val paymentEncoder: Encoder[Payment] = deriveConfiguredEncoder[Payment]
  implicit val paymentDecoder: Decoder[Payment] = deriveConfiguredDecoder[Payment].map { p =>
    p.copy(_id=BSONObjectID.generate())
  }
  // ===========
}

object JSONSerializer extends JSONSerializer
