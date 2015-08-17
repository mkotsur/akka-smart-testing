package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.{ActorLogging, Actor}
import io.github.mkotsur.akka_smart_testing.patterns.SilentActorMessages.{GetState, ChangeState, DoSideEffect}

object SilentActorMessages {

  case class ChangeState(state: String)

  object DoSideEffect

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
      println(msg)
  }

}
