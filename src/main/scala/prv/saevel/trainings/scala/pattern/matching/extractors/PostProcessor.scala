package prv.saevel.trainings.scala.pattern.matching.extractors

object PostProcessor {

  def apply[T](status: Status[T]): Result[T] = status match {
    case ParsingFailed(message) => Left(Error(message, false))
    case EndToEndSuccess(result: T) => Right[Error, T](result)
    case RuntimeProcessingFailure(message) => Left(Error(message, true))
    case NonRuntimeProcessingFailure(message) => Left(Error(message, false))
  }
}
