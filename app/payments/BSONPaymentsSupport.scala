package payments

import common.BaseBSONHandler
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

trait BSONPaymentsSupport extends BaseBSONHandler {
  implicit val paymentHandler: BSONDocumentHandler[Payment] = Macros.handler[Payment]
}
