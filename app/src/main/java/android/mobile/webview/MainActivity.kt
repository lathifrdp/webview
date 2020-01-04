package android.mobile.webview

import android.mobile.webview.webview.WebviewFragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment: Fragment? = null
        fragment = WebviewFragment()

        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.screen_area, fragment)
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        val fr = supportFragmentManager.findFragmentById(R.id.screen_area)
        if (fr is WebviewFragment) {
            this@MainActivity.finish()
            System.exit(0)
        } else {
            super.onBackPressed()
        }
    }
}
