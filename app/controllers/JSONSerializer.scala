package controllers

import reactivemongo.api.bson.BSONObjectID
import io.circe._
import io.circe.generic.extras.Configuration

trait JSONSerializer {

  implicit val config = Configuration.default.withDefaults

  implicit val bsonIdEncoder = Encoder.encodeString.contramap[BSONObjectID](objId => objId.stringify)
  implicit val bsonIdDecoder = Decoder.decodeString.emapTry(BSONObjectID.parse)
}

object JSONSerializer extends JSONSerializer
