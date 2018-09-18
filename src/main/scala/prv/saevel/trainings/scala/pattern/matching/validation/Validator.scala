package prv.saevel.trainings.scala.pattern.matching.validation

object Validator {

  sealed trait ValidationStatus

  case object Valid extends ValidationStatus

  case object Invalid extends  ValidationStatus

  def apply(user: User): ValidationStatus = user match {
    case User(_, _, NonEmptyContactData()) | User(_, PersonalData(_, _, _, Some(_)), _) => user.personalData.age match {
      case None => Valid
      case Some(a) if(a <= 100 && a >= 0) => Valid
      case _ => Invalid
    }
    case  _ => Invalid
  }
}
