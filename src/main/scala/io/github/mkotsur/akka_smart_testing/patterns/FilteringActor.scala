package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.{Actor, ActorRef}
import io.github.mkotsur.akka_smart_testing.patterns.FilteringActorMessages.Id

object FilteringActorMessages {

  case class Id(id: Int)

}

class FilteringActor(nextInChain: ActorRef) extends Actor {

  var ids: Vector[Int] = Vector()

  override def receive: Receive = {
    case msg @ Id(id) if !ids.contains(id) =>
      nextInChain ! msg
      ids = ids :+ id
  }
}
