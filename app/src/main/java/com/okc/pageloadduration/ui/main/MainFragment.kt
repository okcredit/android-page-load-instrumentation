package com.okc.pageloadduration.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.okc.pageloadduration.R
import tech.okcredit.page_load.firstDrawListener

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val createTime = System.currentTimeMillis()
        requireActivity().window.decorView.firstDrawListener(lifecycleScope) {
            Log.d("PageLoad", "Duration=${System.currentTimeMillis() - createTime}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }
}