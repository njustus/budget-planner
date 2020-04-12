package persistence.collections

import javax.inject.{Inject, Singleton}
import persistence.models.Budget
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson._
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BudgetCollection @Inject()(mongo: ReactiveMongoApi)(override implicit val exec: ExecutionContext)
  extends CRUDCollection[Budget] {

  override implicit def handler: BSONDocumentHandler[Budget] = BSONSerializer.budgetHandler

  override def sortOrder: Option[BSONDocument] = Some(BSONSerializer.orderByASC("name"))
  override def collection: Future[BSONCollection] = mongo.database.map(_.collection(BudgetCollection.collectionName))

  def addInvestor(id: String, username: String): Future[Option[Budget]] = collection.flatMap { c =>
    val modifier = BSONDocument("$push" -> BSONDocument("investors" -> username))
    c.update.one(CRUDCollection.byIdSelector(id), modifier)
  }
    .flatMap(_ => this.findById(id))
}

object BudgetCollection {
  val collectionName = "budgets"
}
