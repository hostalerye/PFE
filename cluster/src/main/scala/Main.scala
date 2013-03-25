package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle
import util.Random

/**
 * Copyright (C) 2009-2012 Typesafe Inc. <http://www.typesafe.com>
 */

/*import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._  */



  class Main extends Activity with TypedActivity {
    override def onCreate(bundle: Bundle) {
      super.onCreate(bundle)
      setContentView(R.layout.main)
      // val hello = HelloWorld.run()
      val calc = new CalculatorApplication
      calc.startup()
      val app = new LookupApplication
      println("Started Lookup Application ")
      findView(TR.textview).setText("hello")
      var i = 0
      while (i < 10) {
        if (Random.nextInt(100) % 2 == 0) app.doSomething(Add(Random.nextInt(100), Random.nextInt(100)))
        else app.doSomething(Subtract(Random.nextInt(100), Random.nextInt(100)))
        i += 1
        //Thread.sleep(200)
      }
    }

  }




