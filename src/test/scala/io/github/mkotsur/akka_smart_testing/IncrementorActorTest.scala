package io.github.mkotsur.akka_smart_testing

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestActorRef}
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.Inc
import org.scalatest.{FunSpecLike, Matchers, FunSpec, FunSuite}

class IncrementorActorTest extends TestKit(ActorSystem("test-system")) with FunSpecLike with Matchers {

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

  }

}
