package net.iponweb.tf.demo.domain

import io.circe.{Decoder, HCursor, Json}

final case class RecordWithRequestId(requestId: String, data: Json)

object RecordWithRequestId {
  implicit val decoder: Decoder[RecordWithRequestId] = (c: HCursor) =>
    c.downField("requestId").as[String].map(RecordWithRequestId(_, c.value))
}
