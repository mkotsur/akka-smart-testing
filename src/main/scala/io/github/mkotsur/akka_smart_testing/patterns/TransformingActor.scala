package io.github.mkotsur.akka_smart_testing.patterns

import akka.actor.Actor
import io.github.mkotsur.akka_smart_testing.patterns.TransformingActorMessages.{Question, RightAnswer, WrongAnswer}

import scala.util.Random

object TransformingActorMessages {

  case class Question(q: String)

  case class RightAnswer(value: Int)

  case class WrongAnswer(value: Int)


}

class TransformingActor extends Actor {

  override def receive: Receive = {
    case q: Question =>
      var candidate = 13
      while(!checkAnswer(candidate)) {
        sender ! WrongAnswer(candidate)
        candidate = guessAnswer()
      }

      sender ! RightAnswer(candidate)

  }


  private def guessAnswer(): Int = new Random().nextInt(100)

  private def checkAnswer(value: Int) = value == 42

}
