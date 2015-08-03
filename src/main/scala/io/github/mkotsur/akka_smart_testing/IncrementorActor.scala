package io.github.mkotsur.akka_smart_testing

import akka.actor.Actor
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.Inc

object IncrementorActorMessages {

  case class Inc(i: Int)

}

class IncrementorActor extends Actor {

  var sum: Int = 0

  override def receive: Receive = {
    case Inc(i) =>
      sum = sum + i
  }

}
