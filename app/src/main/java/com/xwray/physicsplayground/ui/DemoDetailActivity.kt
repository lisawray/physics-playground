package com.xwray.physicsplayground.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.xwray.physicsplayground.R



/**
 * An activity representing a single Demo detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [DemoListActivity].
 */
class DemoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_detail)
//        setSupportActionBar(detail_toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setFullScreen()

        if (savedInstanceState == null) {
            with(Demo.valueOf(intent.getStringExtra(DEMO_KEY))) {
                addDemoFragment(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val decorView = window.decorView
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
//        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
//        decorView.systemUiVisibility = uiOptions
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navigateUpTo(Intent(this, DemoListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val DEMO_KEY = "demo_key"
    }
}
