package net.iponweb.tf.demo.domain

import io.circe.Decoder
import io.circe.generic.semiauto._

final case class RecordWithClientId(clientId: String)

object RecordWithClientId {
  implicit val decoder: Decoder[RecordWithClientId] = deriveDecoder
}
