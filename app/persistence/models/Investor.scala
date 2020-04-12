package persistence.models

import io.circe.generic.extras.ConfiguredJsonCodec
import controllers.JSONSerializer._

@ConfiguredJsonCodec
case class Investor(username: String)
