package persistence.models

import controllers.Paginate

case class PaginatedEntity[Entity<:BaseEntity](data: Vector[Entity],
                                   pageNumber:Option[Int],
                                   pageSize: Int,
                                   totalCount: Long) {

  def isPaginated: Boolean = pageNumber.isDefined

  def rangeString: Option[String] = pageNumber.map { page =>
    val start = (page-1)*pageSize
    val end = page*pageSize
      s"$start--${if(end<totalCount) end else totalCount} / $totalCount"
  }
}

object PaginatedEntity {
  def apply[E<:BaseEntity](data: Vector[E], total: Long, paginate:Paginate): PaginatedEntity[E] =
    PaginatedEntity(data, paginate.pageNumber, paginate.pageSize, total)
}
