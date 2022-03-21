package com.okc.pageloadduration.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import tech.okcredit.page_load.PageLoadTracker

class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val activityCreateTime = System.currentTimeMillis()
        PageLoadTracker(window.decorView).onPageLoadListener {
            Log.d(
                "PageLoad",
                "Duration for Compose Activity = ${System.currentTimeMillis() - activityCreateTime}"
            )
        }
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                Greeting()
            }
        }
    }

    @Composable
    private fun Greeting() {
        Text(text = "Hello World")
    }
}