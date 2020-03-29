package persistence.collections

import persistence.models.BaseEntity
import reactivemongo.api.Cursor
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocumentHandler, BSONObjectID}

import scala.concurrent.{ExecutionContext, Future}

trait CRUDCollection[Entity <: BaseEntity] {
  implicit def exec: ExecutionContext
  implicit def handler: BSONDocumentHandler[Entity]
  def collection:Future[BSONCollection]

  def create(entity:Entity): Future[Entity] = collection.flatMap(c => c.insert.one(entity)).map(_ => entity)

  def findAll(): Future[Vector[Entity]] = collection.flatMap { c =>
    c.find(BSONDocument.empty, None)
      .cursor[Entity]()
      .collect[Vector](maxDocs = -1, err = Cursor.FailOnError[Vector[Entity]]())
  }

  def findById(id: String): Future[Option[Entity]] = collection.flatMap{ c =>
    c.find(BSONDocument("_id" -> BSONObjectID.parse(id).get), None)
      .one[Entity]
  }
}
