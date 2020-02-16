package com.example.cjnewskt.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.cjnewskt.R
import com.example.cjnewskt.home.NewsBean
import com.example.cjnewskt.home.TopNewsBean

class NewsActivity : AppCompatActivity(), NewsContract.NewsActivityContract {

    private val newsPresenter = NewsPresenter(this)

    companion object {
        fun activityStart(context: Context, position: Int, isHeader: Boolean) {
            val intent = Intent(context, NewsActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("type", isHeader)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_news)
        onInit()
    }

    override fun onInit() {
        initButtons()
        newsPresenter.initViewPager(intent.getBooleanExtra("type", false))
    }

    override fun visitNews(newsList: List<NewsBean>) {
        val viewPager: ViewPager = findViewById(R.id.web_vp)
        val newsFragmentAdapter = NewsFragmentAdapter(supportFragmentManager)
        val position: Int = intent.getIntExtra("position", 0)
        for (i in newsList.indices) {
            newsList[i].url?.let {
                newsFragmentAdapter.addFragment(NewsFragment.newsInstance(newsList[i].url!!))
            }
        }
        with(viewPager) {
            adapter = newsFragmentAdapter
            offscreenPageLimit = 2
            currentItem = position - newsList[position].count
        }
    }

    override fun visitTopNews(topNewsList: List<TopNewsBean>) {
        val viewPager: ViewPager = findViewById(R.id.web_vp)
        val newsFragmentAdapter = NewsFragmentAdapter(supportFragmentManager)
        val position: Int = intent.getIntExtra("position", 0)
        for (i in topNewsList.indices) {
            newsFragmentAdapter.addFragment(NewsFragment.newsInstance(topNewsList[i].url))
        }
        with(viewPager) {
            adapter = newsFragmentAdapter
            offscreenPageLimit = 1
            currentItem = position
        }
    }

    private fun initButtons() {
        val backButton: ImageButton = findViewById(R.id.web_tb_back)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}