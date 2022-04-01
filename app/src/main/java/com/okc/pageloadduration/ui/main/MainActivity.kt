package com.okc.pageloadduration.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.okc.pageloadduration.R
import tech.okcredit.page_load.firstDrawListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val activityCreateTime = System.currentTimeMillis()
        lifecycleScope.firstDrawListener(window.decorView) {
            Log.d("PageLoad", "Duration=${System.currentTimeMillis() - activityCreateTime}")
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