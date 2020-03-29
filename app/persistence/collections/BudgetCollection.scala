package persistence.collections

import javax.inject.{Inject, Singleton}
import persistence.models.{Account, Budget}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Collection
import reactivemongo.api.bson.Macros

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.bson._
import reactivemongo.api.bson.collection.BSONCollection

@Singleton
class BudgetCollection @Inject()(mongo: ReactiveMongoApi)(implicit exec: ExecutionContext)
  extends BSONSerializer {

  val budgets:Future[BSONCollection] = mongo.database.map(_.collection("budgets"))

  def create(b: Budget): Future[Budget] = {
    val newB = b.copy(_id = Some(BSONObjectID.generate()))
    budgets.flatMap(c => c.insert.one(newB)).map(_ => newB)
  }
}
