package pfe.cluster


import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import concurrent.{Await, Future}
import concurrent.duration
import akka.util.Timeout
import concurrent.duration.{Duration, FiniteDuration}
import java.util.concurrent.TimeUnit


object HelloWorld {


  class HelloActor extends Actor {
    def receive = {
      case "hello" => sender ! "helloWorld"
      case _       => sender ! "dafuq?"
    }
  }


  def runSync():String = {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
    implicit val d:FiniteDuration  = Duration.create(100, TimeUnit.MILLISECONDS);
    implicit val timeout = Timeout(d)
    val future: Future[String] = ask(helloActor, "hello").mapTo[String]
    val result = Await.result(future, timeout.duration)
    result.toString
  }

  def runAsync():Future[String] = {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
    implicit val d:FiniteDuration  = Duration.create(5000, TimeUnit.MILLISECONDS);
    implicit val timeout = Timeout(d)
    val future: Future[String] = ask(helloActor, "hello").mapTo[String]
    future
  }

}