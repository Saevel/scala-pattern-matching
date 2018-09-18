package prv.saevel.trainings.scala.pattern.matching.validation

import org.junit.runner.RunWith
import org.scalacheck.Gen
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.PropertyChecks
import prv.saevel.trainings.scala.pattern.matching.validation.Validator.{Invalid, Valid}

@RunWith(classOf[JUnitRunner])
class ValidationTest extends WordSpec with Matchers with PropertyChecks {

  private def noneGenerator[T]: Gen[Option[T]] = Gen.const(None)

  private def someAlphaString: Gen[Option[String]] = Gen.alphaStr.map(Some(_))

  private val incorrectPesels: Gen[Option[Long]] = Gen.const(None)

  private val correctPesels: Gen[Option[Long]] = Gen.choose(1000, Long.MaxValue).map(Some(_))

  private val incorrectAges: Gen[Option[Int]] = Gen.oneOf[Option[Int]](
    Gen.choose(-1000, -1).map(Some(_)),
    Gen.choose(101, Int.MaxValue).map(Some(_))
  )

  private val correctAges: Gen[Option[Int]] = Gen.oneOf(
    Gen.choose(0, 100).map(Some(_)),
    Gen.const(None)
  )

  private def personalDataByPeselAndAge(peselGenerator: Gen[Option[Long]], ageGenerator: Gen[Option[Int]]): Gen[PersonalData] = for {
    age <- ageGenerator
    pesel <- peselGenerator
    name <- Gen.alphaStr
    surname <- Gen.alphaStr
  } yield PersonalData(name, surname, age, pesel)

  private def correctPersonalData = personalDataByPeselAndAge(correctPesels, correctAges)

  private val incorrectPersonalData = Gen.oneOf(
    personalDataByPeselAndAge(correctPesels, incorrectAges),
    personalDataByPeselAndAge(incorrectPesels, correctAges),
    personalDataByPeselAndAge(incorrectPesels, incorrectAges)
  )

  private def contactData(emailGenerator: Gen[Option[String]],
                          phoneGenerator: Gen[Option[String]],
                          addressGenerator: Gen[Option[String]]): Gen[ContactData] = for {
    email <- emailGenerator
    phone <- phoneGenerator
    address <- addressGenerator
  } yield ContactData(phone, email, address)

  private val incorrectContactData = contactData(noneGenerator, noneGenerator, noneGenerator)

  private val correctContactData = Gen.oneOf(
    contactData(someAlphaString, someAlphaString, someAlphaString),
    contactData(noneGenerator, someAlphaString, someAlphaString),
    contactData(noneGenerator, noneGenerator, someAlphaString),
    contactData(someAlphaString, noneGenerator, someAlphaString),
    contactData(someAlphaString, noneGenerator, noneGenerator),
    contactData(noneGenerator, someAlphaString, noneGenerator),
    contactData(someAlphaString, someAlphaString, noneGenerator)
  )

  private def users(personalDataGenerator: Gen[PersonalData], contactDataGenerator: Gen[ContactData]): Gen[User] = for {
    id <- Gen.alphaStr
    personalData <- personalDataGenerator
    contactData <- contactDataGenerator
  } yield User(id, personalData, contactData)

  private val correctUsers = users(correctPersonalData, correctContactData)

  private val incorrectUsers = users(incorrectPersonalData, incorrectContactData)

  "Validator" when {

    "given users with PESEL or at least one contact information, in the correct age, if defined" should {
      "report them as valid" in forAll(correctUsers){ user =>
        Validator(user) should be(Valid)
      }
    }

    "given users that do not fulfill all validation criteria at once" should {
      "report them as invalid" in forAll(incorrectUsers){ user =>
        Validator(user) should be(Invalid)
      }
    }
  }
}
