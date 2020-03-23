package persistence.tables

import persistence.models.{Account, BaseEntity}
import play.api.Logging

import scala.concurrent.Future

trait Persistence[Entity <: BaseEntity] {
  def findAll: Future[List[Entity]]
  def findById(id:Long): Future[Option[Entity]]
  def deleteById(id:Long): Future[Unit]
  def insert(a:Entity): Future[Entity]
}
