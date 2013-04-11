package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle
import android.widget.{TextView, Button}
import android.view.View
import android.content.Context
import java.io.File
import concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global



class Main extends Activity with TypedActivity {

  var view:TextView = _
  lazy val button = findViewById(R.id.button).asInstanceOf[Button]
  lazy val textView = findViewById(R.id.textview).asInstanceOf[TextView]
  /*def onClick(p1: View) {

    val future_pi:Future[String] = Pi.calculate(nrOfWorkers = 4, nrOfElements = 100, nrOfMessages = 100)
    view.setText("Calculate launch ;")

    future_pi onComplete {
      _ => view.setText("Worker Pi Says : "+future_pi.value.toString+" ;")
    }

  }   */
  implicit def func2OnClickListener(func: (View) => Unit) = {
    new View.OnClickListener() {
      override def onClick(v: View) = func(v)
    }
  }

  implicit def toRunnable[F](f: => F): Runnable =
    new Runnable() { def run() = f }

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    //    val akka_conf = """
    //    akka {
    //      enable-jmx = off
    //      loglevel = DEBUG
    //      actor {
    //        debug {
    //          receive = on
    //          autoreceive = on
    //          lifecycle = on
    //        }
    //      }
    //    }
    //                    """
    //    val stream = openFileOutput("akka.conf", Context.MODE_PRIVATE)
    //    stream.write(akka_conf.getBytes)
    //    stream.close
    //
    //    System.setProperty("akka.config", new File(getFilesDir, "akka.conf").getAbsolutePath)

    view = findView(TR.textview)

    val hello = HelloWorld.runSync()
    view.setText("Worker Sync Says : "+hello)

    val future:Future[String] = HelloWorld.runAsync()
    future onComplete{_ => view.setText(findView(TR.textview).getText()+" \nWorker Async Says : "+future.value.toString)}

    //val button = (findViewById(R.id.buttonCall))
    button.setOnClickListener { view : View =>
    val future_pi:Future[String] = Pi.calculate(nrOfWorkers = 4, nrOfElements = 100, nrOfMessages = 100)
    textView.setText("Calculate launch")

      future_pi onComplete {
        _ =>  println("Worker Pi Says : "+future_pi.value.toString+" ;")
              runOnUiThread{textView.setText("Worker Pi Says : "+future_pi.value.toString+" ;")}
      }

    }

  }

}



