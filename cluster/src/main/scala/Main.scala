package pfe.cluster

import _root_.android.app.Activity
import android.os.{AsyncTask, StrictMode, Bundle}
import util.Random
import akka.actor._
import Actor._
import akka.remote._
import com.typesafe.config.ConfigFactory
import scala.concurrent.ops._

/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

/*import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._  */

class Emitter extends Actor {

  def receive = {
    case (actor: ActorRef, message: PiMessage) =>
      println("~~ wololo ~~")
      // start the calculation
      actor ! message
      context.system.shutdown()
      println("~~ niais ~~")
  }
}



  class Main extends Activity with TypedActivity {
    implicit def toRunnable[F](f: => F): Runnable = new Runnable() { def run() = f }
    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)
      // val hello = HelloWorld.run()
      findView(TR.textview).setText("hello world!")
      spawn {
        println("hello world!")
        try{
          val system = ActorSystem("Main", ConfigFactory.load.getConfig("remotelookup"))
          println("system")
          val emit = system.actorOf(Props[Emitter], name = "emit")
          println("emitter created")
          val master = system.actorFor("akka://PiServer@172.17.6.111:2554/user/master")
          println("master created")
          emit ! (master, Calculate)
          println("launched")
        }
        catch{
          case _ => println("Something went wrong")
        }
      }
    }

  }




