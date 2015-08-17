package io.github.mkotsur.akka_smart_testing.duration

import akka.actor.Props
import akka.testkit.ImplicitSender
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase

import scala.language.postfixOps

class SlowActorTest extends AkkaTestBase with ImplicitSender {


  it("should perform slow operation") {

    val slowActor = system.actorOf(Props(new SlowActor))

    slowActor ! "hello"

    expectMsg("hello")

  }
}
