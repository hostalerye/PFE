package pfe.cluster

import akka.actor._
import Actor._
import java.util.concurrent.CountDownLatch
import akka.routing._
import akka.dispatch.Future
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.util.Duration
import akka.util.duration._

object PiServer extends App{ 
 val system = ActorSystem("PiServer",
    ConfigFactory.load.getConfig("master"))
  val listener = system.actorOf(Props[Listener], name = "listener")
  val actor = system.actorOf(Props(new Master(
    4,10000,10000,listener)), name = "master")
}

 class Listener extends Actor {
    def receive = {
    case PiApproximation(pi, duration) ⇒
      println("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s"
      .format(pi, duration))
      context.system.shutdown()
    }
  }

 
class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef)
    extends Actor {
     
    var pi: Double = _
    var nrOfResults: Int = _
    val start: Long = System.currentTimeMillis
     
    val workerRouter = context.actorOf(
      Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")
     
    def receive = {
     case Calculate ⇒
      for (i ← 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
     case Result(value) ⇒ 
      pi += value 
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        // Send the result to the listener
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start).millis)
        // Stops this actor and all its supervised children
        context.stop(self)
      }
    }
   
  }


