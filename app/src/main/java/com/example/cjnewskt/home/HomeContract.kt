package com.example.cjnewskt.home

import com.example.cjnewskt.netclient.Bean

interface HomeContract {
    interface HomeActivityContract {
        fun initRecyclerView(topNewsList: List<TopNewsBean>, newsList: List<NewsBean>)
        fun onInit()
        fun onUpdate()
        fun onFailure()
        fun setGreetText()
    }

    interface HomePresenterContract {
        fun refresh()
        fun loadMore()
        fun netEnqueue(url: String, bool: Boolean)
        fun getFormatDate(date: String): String
    }

    interface HomeModelContract {
        fun addTopNewsList(bean: Bean)
        fun addNewsList(bean: Bean)
        fun clearLists()
    }
}