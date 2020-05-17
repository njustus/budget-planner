package payments

import common.JSONSerializer
import io.circe._
import io.circe.generic.extras.semiauto._
import reactivemongo.api.bson.BSONObjectID

trait PaymentsJsonSupport extends JSONSerializer {
  implicit val paymentEncoder: Encoder[Payment] = deriveConfiguredEncoder[Payment]
  implicit val paymentDecoder: Decoder[Payment] = deriveConfiguredDecoder[Payment].map { p =>
    p.copy(_id=BSONObjectID.generate())
  }
}
