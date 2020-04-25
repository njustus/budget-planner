package persistence.collections

import controllers.Paginate
import javax.inject.{Inject, Singleton}
import persistence.models.{PaginatedEntity, Payment}
import play.api.Logging
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.api.bson.{BSONDocument, BSONDocumentHandler, BSONObjectID}
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}
import cats.Applicative
import cats.instances.future._

@Singleton
class PaymentCollection @Inject()(mongo: ReactiveMongoApi)(override implicit val exec: ExecutionContext)
  extends CRUDCollection[Payment] with Logging {

  override implicit def handler: BSONDocumentHandler[Payment] = BSONSerializer.paymentHandler

  override def collection: Future[BSONCollection] = mongo.database.map(_.collection(PaymentCollection.collectionName))

  override def sortOrder: Option[BSONDocument] = Some(BSONSerializer.orderByDESC("date"))

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

  override def update(id: String, entity: Payment): Future[Payment] = throw new NotImplementedError("totalAmount update must be handled!")

  def findByAccount(accountId: BSONObjectID, paginate:Paginate): Future[PaginatedEntity[Payment]] = {
    val query = BSONDocument("_accountId" -> accountId)
    val data = collection.flatMap { c =>
      c.find(query, None)
        .sort(this.sortOrder.get)
        .skip(paginate.skipCount)
        .cursor[Payment]()
        .collect[Vector](maxDocs = paginate.maxDocs, err = Cursor.FailOnError[Vector[Payment]]())
    }

    val totalSize = collectionSize(Some(query))

    Applicative[Future].map2(data, totalSize) { (data, size) =>
      PaginatedEntity(data, size, paginate)
    }
  }

}

object PaymentCollection {
  val collectionName = "payments"
}
