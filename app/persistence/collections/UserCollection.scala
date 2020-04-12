package persistence.collections

import javax.inject.{Inject, Singleton}
import persistence.models.AuthUser
import play.api.Logging
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONDocumentHandler}
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserCollection @Inject()(mongo: ReactiveMongoApi)(implicit val exec: ExecutionContext) {

  implicit val handler: BSONDocumentHandler[AuthUser] = BSONSerializer.authUserHandler

  private def collection: Future[BSONCollection] = mongo.connection.database(UserCollection.databaseName).map(_.collection(UserCollection.collectionName))

  def findAll(): Future[Vector[AuthUser]] = collection.flatMap { c =>
    c.find(BSONDocument.empty, None)
      .sort(BSONSerializer.orderByASC("username"))
      .cursor[AuthUser]()
      .collect[Vector](maxDocs = -1, err = Cursor.FailOnError[Vector[AuthUser]]())
  }
}

object UserCollection {
  val databaseName = "services_user"
  val collectionName = "application_records"
}
