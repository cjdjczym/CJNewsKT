package com.example.cjnewskt.news

import com.example.cjnewskt.home.HomeModel

class NewsPresenter(private val newsActivity: NewsContract.NewsActivityContract) : NewsContract.NewsPresenterContract {
    private val homeModel = HomeModel

    override fun initViewPager(isHeader: Boolean) {
        if (isHeader) newsActivity.visitTopNews(homeModel.topNewsList)
        else newsActivity.visitNews(homeModel.newsList)
    }
}