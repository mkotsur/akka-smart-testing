package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.Props
import akka.testkit.ImplicitSender
import io.github.mkotsur.akka_smart_testing.patterns.TransformingActorMessages.{Question, RightAnswer, WrongAnswer}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.Matchers

class TransformingActorTest extends AkkaTestBase with Matchers with ImplicitSender {

  it("should guess answers") {

    val transformingActor = system.actorOf(Props(new TransformingActor()))

    transformingActor ! Question("What is the answer")

    fishForMessage() {
      case WrongAnswer(13) => true
      case _ => false
    }

    fishForMessage() {
      case RightAnswer(42) => true
      case _ => false
    }

  }

}
