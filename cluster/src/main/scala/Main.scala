package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle
import akka.actor.{Props, ActorSystem, Actor}


  class Main extends Activity with TypedActivity {
    var txt = "hello"
    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)


      val system = ActorSystem("HelloSystem")
      // default Actor constructor
      val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
      helloActor ! "hello"

      findView(TR.textview).setText(txt)
    }
    class HelloActor extends Actor {
      def receive = {
        case "hello" => txt =  "hello world"
      }
    }
  }




