package com.example.cjnewskt.news

import com.example.cjnewskt.home.NewsBean
import com.example.cjnewskt.home.TopNewsBean

interface NewsContract {
    interface NewsActivityContract {
        fun onInit()
        fun visitNews(newsList: List<NewsBean>)
        fun visitTopNews(topNewsList: List<TopNewsBean>)
    }

    interface NewsPresenterContract {
        fun initViewPager(isHeader: Boolean)
    }
}