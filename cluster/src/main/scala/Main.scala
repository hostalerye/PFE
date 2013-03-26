package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle
import util.Random
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

/*import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._  */



  class Main extends Activity with TypedActivity {
  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    val system = ActorSystem("PiClient", ConfigFactory.load.getConfig("remotelookup"))
    val master = system.actorFor("akka://PiServer@127.0.0.1:2552/user/master")

    // start the calculation
    master ! Calculate
  }
    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)
      // val hello = HelloWorld.run()
      findView(TR.textview).setText("hello")
      calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)

    }

  }




