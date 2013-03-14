package pfe.cluster


import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.dispatch.{Await, Future}
import akka.pattern.ask
import akka.util.Timeout
import akka.util.duration._
/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 3/13/13
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
object HelloWorld {


  class HelloActor extends Actor {
    def receive = {
      case "hello" => sender ! "helloWorld"
      case _       => sender ! "dafuq?"
    }
  }


  def run():String = {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
    implicit val timeout = Timeout(100 millis)
    val future: Future[String] = ask(helloActor, "hello").mapTo[String]
    /*val result = Await.result(future, timeout.duration)
    result.toString        */
    var res = ""
    future.onComplete(_ => {res = future.value.toString})
    res
  }
}
