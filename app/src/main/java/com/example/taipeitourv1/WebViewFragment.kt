package com.example.taipeitourv1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewFragment : Fragment() {
    private lateinit var layoutView: View
    private lateinit var web_webview: WebView
    private lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_web_view, container, false)
        val bundle = arguments
        if (bundle != null) {
            url = bundle.getString("url").toString()
        }
        initView()
        loadWebView()

        return layoutView
    }

    private fun initView() {
        web_webview = layoutView.findViewById(R.id.Web_WebView)
    }

    private fun loadWebView() {
        web_webview.loadUrl(url)
        web_webview.settings.javaScriptEnabled = true
        web_webview.webViewClient = WebViewClient()
    }
}