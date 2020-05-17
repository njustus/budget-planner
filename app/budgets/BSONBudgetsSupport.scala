package budgets

import common.BSONSerializer
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

trait BSONBudgetsSupport extends BSONSerializer {
  implicit val accountHandler: BSONDocumentHandler[Account] = Macros.handler[Account]

  implicit val budgetHandler: BSONDocumentHandler[Budget] = Macros.handler[Budget]

}
