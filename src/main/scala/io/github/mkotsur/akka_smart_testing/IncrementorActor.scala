package io.github.mkotsur.akka_smart_testing

import akka.actor.Actor
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.{Result, Inc}

object IncrementorActorMessages {

  case class Inc(i: Int)

  case object Result

}

class IncrementorActor extends Actor {

  var sum: Int = 0

  override def receive: Receive = {
    case Inc(i) =>
      if (i < 0) {
        throw new IllegalArgumentException("We are incrementing, not decrementing...")
      }
      sum = sum + i

    case Result =>
      sender() ! sum
  }

}
