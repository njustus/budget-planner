package controllers

import persistence.models._
import io.circe._
import io.circe.generic.semiauto._
import reactivemongo.api.bson.BSONObjectID

trait JSONSerializer {

  implicit val bsonIdEncoder = Encoder.encodeString.contramap[BSONObjectID](objId => objId.stringify)
  implicit val bsonIdDecoder = Decoder.decodeString.emapTry(BSONObjectID.parse)

  implicit val paymentEncoder = deriveEncoder[Payment]
  implicit val paymentDecoder = deriveDecoder[Payment]

  implicit val accountEncoder = deriveEncoder[Account]
  implicit val accountDecoder = deriveDecoder[Account]

  implicit val budgetEncoder = deriveEncoder[Budget]
  implicit val budgetDecoder = deriveDecoder[Budget]
}

object JSONSerializer extends JSONSerializer
