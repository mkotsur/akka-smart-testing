package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.{ActorRefFactory, Props}
import akka.testkit.{ImplicitSender, TestProbe}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase

class HappyParentActorTest extends AkkaTestBase with ImplicitSender {

  describe("Happy parent") {

    it("should retell messages to its child") {

      val childProbe = TestProbe()
      val maker = (_: Any) => childProbe.ref

      val parent = system.actorOf(Props(new HappyParentActor(maker)))

      parent ! "Ping"

      childProbe.expectMsg("Ping")
    }
  }

}
