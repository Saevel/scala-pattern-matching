package prv.saevel.trainings.scala.pattern.matching

import scala.util.Try

package object extractors {

  type Status = (Try[Unit], Try[Int])

  case class Error(message: String, retryable: Boolean)

  type Result[T] = Either[Error, T]
}
