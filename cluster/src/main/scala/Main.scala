package pfe.cluster

import _root_.android.app.Activity
import util.Random
import akka.actor._
import Actor._
import akka.remote._
import com.typesafe.config.ConfigFactory
import scala.concurrent.ops._
import _root_.android.os.Bundle

/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

/*import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._  */

/*class Emitter extends Actor {

  def receive = {
    case (actor: ActorRef, message: PiMessage) =>
      println("~~ wololo ~~")
      // start the calculation
      actor ! message
      context.system.shutdown()
      println("~~ niais ~~")
  }
}     */


  class Main extends Activity with TypedActivity {
    implicit def toRunnable[F](f: => F): Runnable = new Runnable() { def run() = f }

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    println("conf")
    //println(conf.toString)
    // val system = ActorSystem("PiClient", ConfigFactory.load(conf))
    val system = ActorSystem("PiClient", ConfigFactory.load.getConfig("remotelookup"))
    println("system...")
    //val emit = system.actorOf(Props[Emitter], name = "emit")
    //println("emitter created")
    val master = system.actorFor("akka://PiServer@127.0.0.1:2554/user/master")
    println("master created....")

    // start the calculation
    println(master.toString())
    master ! Calculate
  }

    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)
      // val hello = HelloWorld.run()
      spawn {
        println("hello world!!!")
        try{
          println("client")
          calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)
        }
        catch{
          case e:Exception => println("Something went wrong...")
                              e.printStackTrace()
        }
      }
      findView(TR.textview).setText("hello world!!")
    }

  }




