package pfe.cluster

import _root_.android.app.Activity
import _root_.android.os.Bundle

class Main extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.textview).setText("hello, world!")
  }
}
