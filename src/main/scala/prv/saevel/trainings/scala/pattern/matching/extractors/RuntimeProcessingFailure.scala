package prv.saevel.trainings.scala.pattern.matching.extractors

import scala.util.Failure

object RuntimeProcessingFailure {

  def unapply[T](status: Status[T]): Option[(String)] = status match {
    case (_, Failure(e : RuntimeException)) => Some(e.getMessage)
    case _ => None
  }
}
