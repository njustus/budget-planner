package common

import io.circe._
import io.circe.generic.extras.Configuration
import reactivemongo.api.bson.BSONObjectID

trait BaseJsonSupport {

  implicit val config: Configuration = Configuration.default.withDefaults

  implicit val bsonIdEncoder: Encoder[BSONObjectID] = Encoder.encodeString.contramap[BSONObjectID](objId => objId.stringify)
  implicit val bsonIdDecoder: Decoder[BSONObjectID] = Decoder.decodeString.emapTry(BSONObjectID.parse)
}
