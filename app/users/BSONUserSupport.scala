package users

import common.BSONSerializer
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

trait BSONUserSupport extends BSONSerializer {
  implicit val authUserHandler: BSONDocumentHandler[AuthUser] = Macros.handler[AuthUser]
}
