package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle
import akka.actor.{Props, ActorSystem, Actor}


  class Main extends Activity with TypedActivity {
    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)
      val hello = HelloWorld.run()
      findView(TR.textview).setText(hello)
    }

  }




