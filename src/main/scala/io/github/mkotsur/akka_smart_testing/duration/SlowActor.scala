package io.github.mkotsur.akka_smart_testing.duration

import akka.actor.Actor

class SlowActor extends Actor{

  override def receive: Receive = {
    case msg =>

      Thread.sleep(5000)
      sender ! msg
  }

}
