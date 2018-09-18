package prv.saevel.trainings.scala.pattern.matching.extractors

import scala.util.Failure

object NonRuntimeProcessingFailure {

  def unapply[T](status: Status[T]): Option[(String)] = status match {
    case (_, Failure(e : Exception)) => Some(e.getMessage)
    case _ => None
  }
}
