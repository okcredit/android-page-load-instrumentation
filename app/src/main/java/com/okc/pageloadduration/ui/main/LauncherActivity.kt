package com.okc.pageloadduration.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.okc.pageloadduration.R
import tech.okcredit.page_load.firstDrawListener

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val activityCreateTime = System.currentTimeMillis()
        window.decorView.firstDrawListener(lifecycleScope) {
            Log.d("PageLoad", "Duration=${System.currentTimeMillis() - activityCreateTime}")
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_activity)

        findViewById<View>(R.id.xml_view).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<View>(R.id.compose_view).setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}
