package com.okc.pageloadduration.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import tech.okcredit.page_load.firstDrawListener

class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val activityCreateTime = System.currentTimeMillis()
        lifecycleScope.firstDrawListener(window.decorView) {
            Log.d("PageLoad", "Duration=${System.currentTimeMillis() - activityCreateTime}")
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