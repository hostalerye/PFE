package pfe.cluster

import akka.routing.{RoundRobinRouter}
import akka.pattern.ask
import akka.util.Timeout

import akka.actor._
import concurrent.Future
import concurrent.duration.{Duration, FiniteDuration}
import java.util.concurrent.TimeUnit


object Pi {

  sealed trait PiMessage
  case object Calculate extends PiMessage
  case class Work(start: Int, nrOfElements: Int) extends PiMessage
  case class Result(value: Double) extends PiMessage

  class Worker extends Actor {

    def calculatePiFor(start: Int, nrOfElements: Int): Double = {
      var acc = 0.0
      for (i ← start until (start + nrOfElements))
        acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
      acc
    }

    def receive = {
      case Work(start, nrOfElements) ⇒
        sender ! Result(calculatePiFor(start, nrOfElements)) // perform the work
    }
  }

  class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int)
    extends Actor {

    var pi: Double = _
    var nrOfResults: Int = _
    val start: Long = System.currentTimeMillis
    var ref:ActorRef = _

    val workerRouter =  context.actorOf(Props(new Worker).withRouter(new RoundRobinRouter(nrOfWorkers)), name = "workerRouter")

    def receive = {
      case Calculate ⇒
        for (i ← 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
        ref = sender;
      case Result(value) ⇒
        pi += value
        nrOfResults += 1
        if (nrOfResults == nrOfMessages) {
          ref !  "\n\tPi approximation: \t"+("\t%s".format(pi))
          context.stop(self)
        }
    }

  }

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int):Future[String] = {
    // Create an Akka system
    def system = ActorSystem("PiSystem")

    // create the master
    def master = system.actorOf(Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements)),name = "master")

    // start the calculation
    implicit val d:FiniteDuration  = Duration.create(5000, TimeUnit.MILLISECONDS);
    implicit val timeout = Timeout(d)

    val future: Future[String] = ask(master, Calculate).mapTo[String]
    future
  }
}