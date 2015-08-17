package io.github.mkotsur.akka_smart_testing.util

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, FunSpecLike}

abstract class AkkaTestBase extends TestKit(ActorSystem("test-system")) with FunSpecLike with BeforeAndAfterAll {

  override protected def afterAll() {
    super.afterAll()
    system.shutdown()
    system.awaitTermination()
  }

}
