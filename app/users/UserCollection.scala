package users

import common.BaseBSONHandler
import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.BSONDocument

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserCollection @Inject()(mongo: ReactiveMongoApi)(implicit val exec: ExecutionContext)
  extends BSONUserSupport
  with Logging {

  private def collection: Future[BSONCollection] = mongo.connection.database(UserCollection.databaseName).map(_.collection(UserCollection.collectionName))

  def findAll(): Future[Vector[AuthUser]] = collection.flatMap { c =>
    c.find(BSONDocument.empty, None)
      .sort(BaseBSONHandler.orderByASC("username"))
      .cursor[AuthUser]()
      .collect[Vector](maxDocs = -1, err = Cursor.FailOnError[Vector[AuthUser]]())
  }
}

object UserCollection {
  val databaseName = "services_user"
  val collectionName = "application_records"
}
