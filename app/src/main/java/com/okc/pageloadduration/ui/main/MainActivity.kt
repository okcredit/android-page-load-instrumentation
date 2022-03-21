package com.okc.pageloadduration.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import tech.okcredit.page_load.PageLoadTracker
import com.okc.pageloadduration.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val activityCreateTime = System.currentTimeMillis()
        PageLoadTracker(window.decorView).onPageLoadListener {
            Log.d(
                "PageLoad",
                "Duration for Main Activity = ${System.currentTimeMillis() - activityCreateTime}"
            )
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}