package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.Props
import io.github.mkotsur.akka_smart_testing.patterns.FilteringActorMessages.Id
import io.github.mkotsur.akka_smart_testing.patterns.SilentActorMessages.{GetState, ChangeState}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.{Matchers, FunSuite}

class FilteringActorTest extends AkkaTestBase with Matchers {

  it("should not send same ID twice") {

    val filteringActor = system.actorOf(Props(new FilteringActor(testActor)))

    filteringActor ! Id(1)
    filteringActor ! Id(2)
    filteringActor ! Id(3)
    filteringActor ! Id(2)
    filteringActor ! Id(5)
    filteringActor ! Id(4)
    filteringActor ! Id(8)

//    expectMsg(Id(1))
//    expectMsg(Id(2))
//    expectMsg(Id(3))
//    expectMsg(Id(5))
//    expectMsg(Id(4))
//    expectMsg(Id(8))

    val msgs = receiveWhile() {
      case Id(n) if n <=8 => n
    }

    msgs shouldBe List(1, 2, 3, 5, 4, 8)
  }

}
