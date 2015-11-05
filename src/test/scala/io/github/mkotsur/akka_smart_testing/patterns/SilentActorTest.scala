package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.Props
import akka.testkit.{EventFilter, ImplicitSender}
import io.github.mkotsur.akka_smart_testing.patterns.SilentActorMessages.{DoSideEffectWithException, DoSideEffect, ChangeState, GetState}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase

class SilentActorTest extends AkkaTestBase with ImplicitSender {

  val silentActor = system.actorOf(Props(new SilentActor))

  it("should change the state") {
    silentActor ! ChangeState("new state")

    expectNoMsg()

    silentActor ! GetState

    expectMsg("new state")
  }

  it("should do some side effects") {
    EventFilter.info(message = "side effect", occurrences = 1) intercept {
      silentActor ! DoSideEffect
    }
  }

  it("should do some side effects with exceptions") {
    EventFilter[RuntimeException](message = "This side effect causes an exception", occurrences = 1) intercept {
      silentActor ! DoSideEffectWithException
    }
  }

}
