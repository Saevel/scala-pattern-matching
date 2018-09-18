package prv.saevel.trainings.scala.pattern.matching.extractors

import scala.util.Success

object EndToEndSuccess {

  def unapply[T](status: Status[T]): Option[T] = status match {
    case (Success(_), Success(i)) => Some(i)
    case _ => None
  }
}
