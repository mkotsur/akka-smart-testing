package io.github.mkotsur.akka_smart_testing

import akka.testkit.TestActorRef
import akka.util.Timeout
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.Inc
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.Matchers._
import scala.concurrent.duration._

import scala.language.postfixOps

class IncrementorActorTest extends AkkaTestBase {

  implicit val timeout: Timeout = 1 second

  describe("Incrementor actor") {

    it("should have sum = 0 by default") {
      val actorRef = TestActorRef[IncrementorActor]

      actorRef.underlyingActor.sum shouldEqual 0
    }

    it("should increment on new messages") {
      val actorRef = TestActorRef[IncrementorActor]

      actorRef ! Inc(2)
      actorRef.underlyingActor.sum shouldEqual 2

      actorRef ! Inc(3)
      actorRef.underlyingActor.sum shouldEqual 5
    }

    it("should throw an exception when negative value is passed") {
      val actorRef = TestActorRef[IncrementorActor]

      intercept[IllegalArgumentException] {
        actorRef.receive(Inc(-1))
      }
    }

  }

}
