package com.example.cjnewskt.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.cjnewskt.R

class NewsFragment : Fragment() {
    private var webView: WebView? = null

    companion object {
        fun newsInstance(url: String): NewsFragment {
            val fragment = NewsFragment()
            val args = Bundle()
            args.putString("url", url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_fragment_body, container, false)
        webView=view.findViewById(R.id.web_news)
        with(webView!!){
            settings.javaScriptEnabled=true
            webViewClient = WebViewClient()
            if (savedInstanceState != null) {
                restoreState(savedInstanceState)
            } else {
                loadUrl(arguments!!.getString("url"))
            }
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView!!.saveState(outState)
    }
}
