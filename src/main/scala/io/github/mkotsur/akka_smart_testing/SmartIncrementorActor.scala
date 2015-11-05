package io.github.mkotsur.akka_smart_testing

import akka.actor.Actor
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.{Result, Inc}

import scala.concurrent.Future

object SmartIncrementorActorMessages {

  case class Inc(i: Int)

  case object Result

}

class SmartIncrementorActor extends Actor {

  var sum: Int = 0

  override def receive: Receive = {
    case Inc(i) =>
      import scala.concurrent.ExecutionContext.Implicits.global
      Future {
        Thread.sleep(100)
        sum = sum + i
      }
  }

}