package prv.saevel.trainings.scala.pattern.matching.validation

object Validator {

  sealed trait ValidationStatus

  case object Valid extends ValidationStatus

  case object Invalid extends  ValidationStatus

  def apply(user: User): ValidationStatus = ???
}
