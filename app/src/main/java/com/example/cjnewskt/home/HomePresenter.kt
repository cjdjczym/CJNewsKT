package com.example.cjnewskt.home

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import com.example.cjnewskt.netclient.Bean
import com.example.cjnewskt.netclient.Client
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.text.ParseException
import java.util.*

class HomePresenter(private val homeView: HomeContract.HomeActivityContract) :
    HomeContract.HomePresenterContract {

    private val homeModel = HomeModel
    var isComplete = false

    override fun getFormatDate(date: String): String {
        val dft = java.text.SimpleDateFormat("yyyyMMdd", Locale("zh", "CN"))
        try {
            val temp = dft.parse(date)
            if (temp != null) {
                return java.text.SimpleDateFormat("MM月dd日", Locale("zh", "CN")).format(temp)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return "TimeError"
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val bean: Bean = Client.invoke(msg.obj.toString())
            if (msg.what == 1) {
                NewsBean.lineCount = 0
                with(homeModel) {
                    date = bean.date
                    clearLists()
                    addNewsList(bean)
                    addTopNewsList(bean)
                }
                homeView.initRecyclerView(homeModel.topNewsList, homeModel.newsList)
            }
            if (msg.what == 0) {
                with(homeModel) {
                    date = bean.date
                    newsList.add(NewsBean(getFormatDate(homeModel.date!!), null, null, null))
                    addNewsList(bean)
                }
                homeView.onUpdate()
            }
            isComplete = true
        }
    }

    override fun netEnqueue(url: String, bool: Boolean) {
        Client.sendOKHttpRequest(url, object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                homeView.onFailure()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val result = response.body()!!.string()
                    val message = Message.obtain()
                    if (bool) message.what = 1
                    else message.what = 0
                    message.obj = result
                    handler.sendMessage(message)
                }
            }
        })
    }

    override fun refresh() {
        isComplete = false
        netEnqueue("https://news-at.zhihu.com/api/3/news/latest", true)
    }

    override fun loadMore() {
        isComplete = false
        netEnqueue("https://news-at.zhihu.com/api/3/news/before/${homeModel.date}", false)
    }
}