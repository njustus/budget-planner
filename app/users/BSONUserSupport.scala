package users

import common.BaseBSONHandler
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

trait BSONUserSupport extends BaseBSONHandler {
  implicit val authUserHandler: BSONDocumentHandler[AuthUser] = Macros.handler[AuthUser]
}
