package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle
import util.Random
import akka.actor._
import com.typesafe.config.ConfigFactory

/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

/*import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._  */

class Emitter extends Actor {
  val system = ActorSystem("PiClient", ConfigFactory.load.getConfig("remotelookup"))
  val master = system.actorFor("akka.tcp://PiServer@172.17.6.111:2552/user/master")

  def receive = {
    case _ =>
      println("wololo")
      // start the calculation
      master ! Calculate
      context.system.shutdown()
      println("derpderp")
  }
}

  class Main extends Activity with TypedActivity {

    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)
      // val hello = HelloWorld.run()
      findView(TR.textview).setText("hello world!")
      println("hello world!")
      val system = ActorSystem()
      val emit = system.actorOf(Props[Emitter], name = "emit")
      println("emitter created")
      emit ! "launch"
      println("launched")

    }

  }




