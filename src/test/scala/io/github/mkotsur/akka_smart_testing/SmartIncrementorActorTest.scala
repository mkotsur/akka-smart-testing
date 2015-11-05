package io.github.mkotsur.akka_smart_testing

import akka.testkit.{ImplicitSender, TestActorRef}
import akka.util.Timeout
import io.github.mkotsur.akka_smart_testing.IncrementorActorMessages.Inc
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.Matchers._

import scala.concurrent.duration._
import scala.language.postfixOps

class SmartIncrementorActorTest extends AkkaTestBase with ImplicitSender {

  implicit val timeout: Timeout = 1 second

  ignore("Smart incrementor actor in a single thread") {

    it("should increment on new messages") {
      val actorRef = TestActorRef[SmartIncrementorActor]

      actorRef ! Inc(2)
      actorRef.underlyingActor.sum shouldEqual 2

      actorRef ! Inc(3)
      actorRef.underlyingActor.receive(Inc(3))
    }

  }

}
