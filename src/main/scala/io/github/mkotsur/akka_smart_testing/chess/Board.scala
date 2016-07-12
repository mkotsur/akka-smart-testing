package io.github.mkotsur.akka_smart_testing.chess

import akka.actor.{Actor, ActorLogging, PoisonPill, Props, ReceiveTimeout}
import akka.actor.Actor.Receive
import akka.actor.Status.Failure
import io.github.mkotsur.akka_smart_testing.chess.Board.Move
import akka.util.Timeout

import scala.concurrent.duration._
import scala.language.postfixOps

object Board {

  def props() = Props(new Board())

  case class Move(player: String, beginPosition: String, endPosition: String)

}

class Board extends Actor with ActorLogging {

  override def preStart() = {
    context.setReceiveTimeout(1 second)
  }

  override def receive: Receive = receiveMoveOf(self.path.name.split("_").head)

  def receiveMoveOf(player: String): Receive = {
    case Move(`player`, _, _) =>
      log.info(s"Handling the move of $player")
    case Move(anotherPlayer, _, _) =>
      sender() ! Failure(new RuntimeException(s"$anotherPlayer this is not your turn!"))
    case ReceiveTimeout =>
      log.warning(s"Shutting down game ${self.path.name} because of the inactivity timeout.")
      self ! PoisonPill
  }

}
