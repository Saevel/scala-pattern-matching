package prv.saevel.trainings.scala.pattern.matching.extractors

import scala.util.Failure

object ParsingFailed {

  def unapply[T](status: Status[T]): Option[String] = status match {
    case (Failure(e), _ ) => Some(e.getMessage)
    case _ => None
  }

}
