package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.{ActorRefFactory, Actor, ActorRef, Props}

class HappyParentActor(childMaker: ActorRefFactory => ActorRef) extends Actor {

  val child: ActorRef = childMaker(context)

  override def receive: Receive = {
    case msg => child.forward(msg)
  }

}
