package com.okc.pageloadduration.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.okcredit.page_load.PageLoadTracker
import com.okc.pageloadduration.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentCreateTime = System.currentTimeMillis()
        PageLoadTracker(requireActivity().window.decorView).onPageLoadListener {
            Log.d(
                "PageLoad",
                "Duration for Main Fragment = ${System.currentTimeMillis() - fragmentCreateTime}"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }
}