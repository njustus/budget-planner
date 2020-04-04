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
  def sortOrder: Option[BSONDocument] = None

  def create(entity:Entity): Future[Entity] = collection.flatMap(c => c.insert.one(entity)).map(_ => entity)

  def findAll(): Future[Vector[Entity]] = collection.flatMap { c =>
    val findQuery = sortOrder match {
      case Some(sortOrder) => c.find(BSONDocument.empty, None).sort(sortOrder)
      case None => c.find(BSONDocument.empty, None)
    }

    findQuery
      .cursor[Entity]()
      .collect[Vector](maxDocs = -1, err = Cursor.FailOnError[Vector[Entity]]())
  }

  def findById(id: String): Future[Option[Entity]] = collection.flatMap{ c =>
    c.find(CRUDCollection.byIdSelector(id), None)
      .one[Entity]
  }
}

object CRUDCollection {
  def byIdSelector(id: BSONObjectID): BSONDocument = BSONDocument("_id" -> id)
  def byIdSelector(id: String): BSONDocument = byIdSelector(BSONObjectID.parse(id).get)
}
