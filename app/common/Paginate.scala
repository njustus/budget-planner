package common

case class Paginate(pageSize: Int, pageNumber: Option[Int]) {

  def skipCount: Int = pageNumber.map(cnt => pageSize*(cnt-1)).getOrElse(0)

  def maxDocs: Int = pageNumber.map(_ => pageSize).getOrElse(-1)
}
