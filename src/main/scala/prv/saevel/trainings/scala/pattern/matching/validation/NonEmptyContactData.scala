package prv.saevel.trainings.scala.pattern.matching.validation

object NonEmptyContactData {

  def unapply(arg: ContactData): Boolean = arg.address.isDefined || arg.phone.isDefined || arg.email.isDefined
}
