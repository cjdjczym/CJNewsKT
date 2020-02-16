package com.example.cjnewskt.home

import com.example.cjnewskt.netclient.Bean
import java.io.Serializable

data class NewsBean(
    val title: String,
    val hint: String?,
    val image: String?,
    val url: String?
) {
    var count: Int = 0

    companion object {
        var lineCount: Int = 0
    }

    init {
        if (hint == null) lineCount++
        else count = lineCount
    }
}

data class TopNewsBean(
    val title: String,
    val hint: String,
    val image: String,
    val url: String,
    val position: Int
) : Serializable

object HomeModel : HomeContract.HomeModelContract {
    var topNewsList = mutableListOf<TopNewsBean>()
    var newsList = mutableListOf<NewsBean>()
    var date: String? = null

    override fun addTopNewsList(bean: Bean) {
        var title: String
        var hint: String
        var image: String
        var url: String
            for (i in bean.top_stories.indices) {
                title = bean.top_stories[i].title
                hint = bean.top_stories[i].hint
                image = bean.top_stories[i].image
                url = bean.top_stories[i].url
                topNewsList.add(TopNewsBean(title, hint, image, url, i))
            }
    }

    override fun addNewsList(bean: Bean) {
        var title: String
        var hint: String
        var image: String
        var url: String
        for (i in bean.stories.indices) {
            title = bean.stories[i].title
            hint = bean.stories[i].hint
            image = bean.stories[i].images[0]
            url = bean.stories[i].url
            newsList.add(NewsBean(title, hint, image, url))
        }
    }

    override fun clearLists() {
        newsList.clear()
        topNewsList.clear()
    }
}

