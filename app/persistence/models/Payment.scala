package persistence.models

case class Payment(name: String,
                   description:Option[String],
                   amount: Double,
                   _accountId:Long,
                   _id: Long)
