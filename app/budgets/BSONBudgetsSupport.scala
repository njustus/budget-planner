package budgets

import common.BaseBSONHandler
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

trait BSONBudgetsSupport extends BaseBSONHandler {
  implicit val accountHandler: BSONDocumentHandler[Account] = Macros.handler[Account]

  implicit val budgetHandler: BSONDocumentHandler[Budget] = Macros.handler[Budget]

}
