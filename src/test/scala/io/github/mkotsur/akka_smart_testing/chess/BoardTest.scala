package io.github.mkotsur.akka_smart_testing.chess

import akka.actor.{ActorRef, PoisonPill}
import akka.pattern.ask
import akka.testkit.ImplicitSender
import akka.util.Timeout
import io.github.mkotsur.akka_smart_testing.chess.Board.Move
import io.github.mkotsur.akka_smart_testing.chess.Room.{CreateBoard}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures._

import scala.concurrent.duration._
import scala.language.postfixOps
import akka.testkit._

class BoardTest extends AkkaTestBase with ImplicitSender with Matchers {

  implicit val timeout: Timeout = (1 second).dilated

  describe("Board actor") {

    it("should not allow the first move to the blacks") {
      val boardActor = system.actorOf(Board.props(), "player1_player2")
      whenReady((boardActor ? Move("player2", "e2", "e4")).failed) { ex =>
        ex.asInstanceOf[RuntimeException].getMessage should include ("not your turn")
      }

      boardActor ! PoisonPill
    }


    it("should end the game after a certain timeout") {
      val boardActor = system.actorOf(Board.props(), "player1_player2")
      watch(boardActor)
      expectTerminated(boardActor)
    }
  }
}
