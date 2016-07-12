package io.github.mkotsur.akka_smart_testing.chess

import akka.actor.ActorRef
import akka.actor.Status.Failure
import akka.pattern.ask
import akka.testkit.ImplicitSender
import akka.util.Timeout
import io.github.mkotsur.akka_smart_testing.chess.Room.{CreateBoard, GamesInProgress}
import io.github.mkotsur.akka_smart_testing.util.AkkaTestBase
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures._

import scala.concurrent.duration._
import scala.language.postfixOps

class RoomTest extends AkkaTestBase with ImplicitSender with Matchers {

  implicit val timeout: Timeout = 1 second

  describe("Room actor") {
    it("should not create 2 boards for the same players") {
      val roomActor = system.actorOf(Room.props())
      val firstBoardFuture = roomActor ? CreateBoard("p1", "p2")
      whenReady(firstBoardFuture) { firstBoard =>
        firstBoard shouldBe a [ActorRef]
        firstBoard.asInstanceOf[ActorRef].path.name shouldBe "p1_p2"
      }

      val secondBoardFuture = roomActor ? CreateBoard("p1", "p2")
      whenReady(secondBoardFuture.failed) { secondBoardFailure =>
        secondBoardFailure shouldBe a [RuntimeException]
        secondBoardFailure.asInstanceOf[RuntimeException].getMessage should include("already exists")
      }
    }

    it("should respond with the list of games in progress") {
      val roomActor = system.actorOf(Room.props())
      roomActor ! CreateBoard("p1", "p2")
      roomActor ! CreateBoard("p3", "p4")

      whenReady(roomActor ? GamesInProgress) { boardActors =>
        boardActors shouldBe a [GamesInProgress]
        val paths = boardActors.asInstanceOf[GamesInProgress].games.map(_.path.name)
        paths should have size 2
        paths shouldEqual Seq("p1_p2", "p3_p4")
      }
    }
  }
}
