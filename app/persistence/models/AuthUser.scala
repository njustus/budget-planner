package persistence.models

import io.circe.generic.JsonCodec

@JsonCodec
case class AuthUser(username:String,
                    email: Option[String],
                    first_name: Option[String],
                    last_name: Option[String])
