package com.xwray.physicsplayground.ui

import android.app.Activity
import android.support.annotation.ArrayRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.xwray.physicsplayground.R


fun Activity.setFullScreen() {
    findViewById<View>(android.R.id.content).apply {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}

fun AppCompatActivity.addDemoFragment(demo: Demo) {
    FragmentFactory.create(demo).apply {
        supportFragmentManager.beginTransaction()
                .add(R.id.demo_detail_container, this)
                .commit()
    }
}

fun Fragment.getColorArray(@ArrayRes colorArrayRes: Int): IntArray {
    return resources.getIntArray(colorArrayRes)
}
