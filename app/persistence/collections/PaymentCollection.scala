package persistence.collections

import javax.inject.{Inject, Singleton}
import persistence.models.Payment
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson._
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentCollection @Inject()(mongo: ReactiveMongoApi)(override implicit val exec: ExecutionContext)
  extends CRUDCollection[Payment] {

  override implicit def handler: BSONDocumentHandler[Payment] = BSONSerializer.paymentHandler

  override def collection: Future[BSONCollection] = mongo.database.map(_.collection("budgets"))
}
