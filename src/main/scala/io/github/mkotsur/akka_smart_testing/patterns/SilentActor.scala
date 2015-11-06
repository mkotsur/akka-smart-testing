package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.{SupervisorStrategy, OneForOneStrategy, ActorLogging, Actor}
import io.github.mkotsur.akka_smart_testing.patterns.SilentActorMessages.{DoSideEffectWithException, GetState, ChangeState, DoSideEffect}

object SilentActorMessages {

  case class ChangeState(state: String)

  object DoSideEffect

  object DoSideEffectWithException

  object GetState

}

class SilentActor extends Actor with ActorLogging {

  var state = "-no-state-"

  override def receive: Receive = {

    case GetState =>
      sender ! state

    case ChangeState(newState: String) =>
      state = newState

    case DoSideEffect =>
      val msg = "side effect"
      log.info(msg)

    case DoSideEffectWithException =>
      val msg = "This side effect causes an exception"
      log.error(new RuntimeException(msg), msg)
  }

}
