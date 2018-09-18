package prv.saevel.trainings.scala.pattern.matching.extractors

import java.io.{FileNotFoundException, IOException}

import org.scalacheck.Gen
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.prop.PropertyChecks

import scala.util.{Failure, Success, Try}


class PostProcessorTest extends WordSpec with Matchers with PropertyChecks {

  private val exceptionMessage = "thrown on purpose"

  private val parseSuccess: Gen[Try[Unit]] = Gen.const(Success({}))

  private val parseFailure: Gen[Try[Unit]] = Gen.const(Failure(ParserException))

  private val executionSuccess: Gen[Try[Int]] = Gen.choose(0, 100).map(Success(_))

  private val executionRuntimeFailure: Gen[Try[Int]] = Gen.oneOf[Exception](
    new IllegalArgumentException(exceptionMessage),
    new IllegalStateException(exceptionMessage),
    new ArithmeticException(exceptionMessage)
  ).map(Failure(_))

  private val executionFailure: Gen[Try[Int]] = Gen.oneOf[Exception](
    new IOException(exceptionMessage),
    new FileNotFoundException(exceptionMessage)
  ).map(Failure(_))

  "PostProcessor" when {

    "parsing and execution succeeded" should {

      "return the correct result" in forAll(parseSuccess, executionSuccess) { (first, second) =>
        PostProcessor((first, second)) should be(Right(second.get))
      }
    }

    "parsing failed" should {

      "report an unretryable failure" in forAll(parseFailure, Gen.oneOf(executionSuccess, executionFailure)){ (first, second) =>
        PostProcessor((first, second)) should be(Left(Error(second.failed.get.getMessage, false)))
      }
    }

    "parsing succeeded and execution failed with a runtime exception" should {

      "return a retryable failure" in forAll(parseSuccess, executionFailure) { (first, second) =>
        PostProcessor((first, second)) should be(Left(Error(second.failed.get.getMessage, true)))
      }
    }

    "parsing succeeded and execution failed with a runtime exception" should {

      "return a non-retryable failure" in forAll(parseSuccess, executionFailure) { (first, second) =>
        PostProcessor((first, second)) should be(Left(Error(second.failed.get.getMessage, false)))
      }
    }
  }
}
