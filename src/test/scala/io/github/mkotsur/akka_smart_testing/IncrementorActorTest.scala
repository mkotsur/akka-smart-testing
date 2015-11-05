package io.github.mkotsur.akka_smart_testing

import akka.actor.Props
import akka.testkit.{TestProbe, ImplicitSender, TestActorRef}
import akka.util.Timeout
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.{Result, Inc}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.Matchers._
import scala.concurrent.duration._

import scala.language.postfixOps

class IncrementorActorTest extends AkkaTestBase with ImplicitSender {

  implicit val timeout: Timeout = 1 second

  describe("Incrementor actor in a single thread") {

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

  describe("Incrementor actor in multiple threads") {

    it("should have sum = 0 by default") {
      val actorRef = system.actorOf(Props(classOf[IncrementorActor]))

      actorRef ! Result

      expectMsg(0)
    }

    it("should increment on new messages") {
      val actorRef = system.actorOf(Props(classOf[IncrementorActor]))

      actorRef ! Inc(2)
      actorRef ! Result

      expectMsg(2)

      actorRef ! Inc(3)
      actorRef ! Result

      expectMsg(5)
    }

    it("should increment on new messages 2") {
      val actorRef = system.actorOf(Props(classOf[IncrementorActor]))

      val probe = TestProbe()

      actorRef.tell(Inc(2), probe.ref)
      actorRef.tell(Result, probe.ref)

      probe.expectMsg(2)
    }

    it("should fish for messages") {
      val actorRef = system.actorOf(Props(classOf[IncrementorActor]))

      val probe = TestProbe()

      actorRef.tell(Inc(2), probe.ref)
      actorRef.tell(Result, probe.ref)

      val msg: Any = probe.fishForMessage() {
        case n: Int => true
      }

      msg shouldBe 2
    }
  }

}
