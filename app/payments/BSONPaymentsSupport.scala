package payments

import common.BSONSerializer
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

trait BSONPaymentsSupport extends BSONSerializer {
  implicit val paymentHandler: BSONDocumentHandler[Payment] = Macros.handler[Payment]
}
