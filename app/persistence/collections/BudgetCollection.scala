package persistence.collections

import controllers.Paginate
import javax.inject.{Inject, Singleton}
import persistence.models.{Budget, PaginatedEntity}
import play.api.Logging
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.bson._
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BudgetCollection @Inject()(mongo: ReactiveMongoApi)(override implicit val exec: ExecutionContext)
  extends CRUDCollection[Budget]
  with Logging {

  override implicit def handler: BSONDocumentHandler[Budget] = BSONSerializer.budgetHandler

  override def sortOrder: Option[BSONDocument] = Some(BSONSerializer.orderByASC("name"))
  override def collection: Future[BSONCollection] = mongo.database.map(_.collection(BudgetCollection.collectionName))

  def findByInvestor(investorUsername: String, paginate: Paginate): Future[PaginatedEntity[Budget]] = {
    val query = BSONDocument("investors" -> BSONDocument("$in" -> BSONArray(investorUsername)))
    logger.debug(s"findByInvestor: $query")
    findByQuery(query)(paginate)
  }

  def updateInvestors(id: String, investors: Seq[String]): Future[Option[Budget]] = {
    import BSONSerializer._
    for {
      bsonArray <- Future.fromTry(BSON.write(investors))
      c <- collection
      modifier = BSONDocument("$set" -> BSONDocument("investors" -> bsonArray))
      _ <- c.update.one(CRUDCollection.byIdSelector(id), modifier)
      optBudget <- this.findById(id)
    } yield optBudget
  }
}

object BudgetCollection {
  val collectionName = "budgets"
}
