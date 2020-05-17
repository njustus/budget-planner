package payments

import budgets.BudgetCollection
import common.{BSONSerializer, CRUDCollection, Paginate, PaginatedEntity}
import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONDocumentHandler, BSONObjectID}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentCollection @Inject()(mongo: ReactiveMongoApi)(override implicit val exec: ExecutionContext)
  extends CRUDCollection[Payment]
  with BSONPaymentsSupport
    with Logging {

  override implicit def handler: BSONDocumentHandler[Payment] = paymentHandler

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
    logger.debug(s"findByAccount: $query")
    findByQuery(query)(paginate)
  }
}

object PaymentCollection {
  val collectionName = "payments"
}