package persistence.collections

import javax.inject.{Inject, Singleton}
import persistence.models.{Budget, Payment}
import play.api.Logging
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Collection, Cursor, DB, DefaultDB, QueryOpts}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentHandler, BSONObjectID}
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentCollection @Inject()(mongo: ReactiveMongoApi)(override implicit val exec: ExecutionContext)
  extends CRUDCollection[Payment] with Logging {

  override implicit def handler: BSONDocumentHandler[Payment] = BSONSerializer.paymentHandler

  override def collection: Future[BSONCollection] = mongo.database.map(_.collection(PaymentCollection.collectionName))

  override def create(entity: Payment): Future[Payment] = {
    val accountSelector = BSONDocument("accounts._id" -> entity._accountId)
    val updateStm = BSONDocument( "$inc" -> BSONDocument("accounts.$.totalAmount" -> entity.amount) )

    for {
      result <- super.create(entity)
      collection <- mongo.database.map(_.collection[BSONCollection](BudgetCollection.collectionName))
      writeResult <- collection.update.one(accountSelector, updateStm)
      _ = logger.debug(s"updated amount result: $writeResult")
    } yield result
  }

  override def findAll(): Future[Vector[Payment]] = collection.flatMap { c =>
        c.find(BSONDocument.empty, None)
      .sort(BSONSerializer.orderByDESC("date"))
      .cursor[Payment]()
      .collect[Vector](maxDocs = -1, err = Cursor.FailOnError[Vector[Payment]]())
  }

  def findByAccount(accountId: BSONObjectID): Future[Vector[Payment]] = {
    val query = BSONDocument("_accountId" -> accountId)
    collection.flatMap { c =>
      c.find(query, None)
        .cursor[Payment]()
        .collect[Vector](maxDocs = -1, err = Cursor.FailOnError[Vector[Payment]]())
    }
  }

}

object PaymentCollection {
  val collectionName = "payments"
}
