package net.iponweb.tf.demo.domain

import io.circe.{Encoder, Json}
import io.circe.generic.semiauto._

final case class JoinResult(requestId: String, data: Map[String, List[Json]])

object JoinResult {

  implicit val encoder: Encoder[JoinResult] = deriveEncoder
}
