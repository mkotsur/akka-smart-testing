package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.{PoisonPill, Props}
import akka.testkit.TestProbe
import io.github.mkotsur.akka_smart_testing.patterns.FilteringActorMessages.Id
import io.github.mkotsur.akka_smart_testing.patterns.SilentActorMessages.{GetState, ChangeState}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.{Matchers, FunSuite}
import scala.concurrent.duration._
import scala.language.postfixOps

class FilteringActorTest extends AkkaTestBase with Matchers {

  it("should not send same ID twice") {

    val probe = TestProbe()


    val filteringActor = system.actorOf(Props(new FilteringActor(probe.ref)))

    probe.watch(filteringActor)

    filteringActor ! Id(1)
    filteringActor ! Id(2)
    filteringActor ! Id(3)
    filteringActor ! Id(2)
    filteringActor ! Id(5)
    filteringActor ! Id(4)
    filteringActor ! Id(8)


    val msgs = probe.receiveN(6).map(_.asInstanceOf[Id].id).toList

    msgs shouldBe List(1, 2, 3, 5, 4, 8)
  }

}
