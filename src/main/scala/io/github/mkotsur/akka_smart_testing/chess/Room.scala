package io.github.mkotsur.akka_smart_testing.chess

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorRef, Props}
import io.github.mkotsur.akka_smart_testing.chess.Room.{CreateBoard, GamesInProgress}


object Room {

  case class CreateBoard(player1: String, player2: String)

  case object GamesInProgress
  case class GamesInProgress(games: List[ActorRef])

  def props() = Props(new Room)

}

class Room extends Actor {

  override def receive: Receive = {
    case CreateBoard(player1, player2) =>
      val boardName = s"${player1}_$player2"
      context.child(boardName) match {
        case Some(board) =>
          sender() ! Failure(new RuntimeException(s"The board with $boardName already exists"))
        case None =>
          val boardActor = context.actorOf(Board.props(), boardName)
          sender() ! boardActor
      }
    case GamesInProgress =>
      sender() ! GamesInProgress(context.children.toList)
  }

}
