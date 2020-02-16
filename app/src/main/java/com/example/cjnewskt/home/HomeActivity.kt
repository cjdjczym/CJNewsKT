package com.example.cjnewskt.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cjnewskt.R
import com.example.cjnewskt.user.UserActivity
import java.util.*

class HomeActivity : AppCompatActivity(), HomeContract.HomeActivityContract {

    private val homePresenter = HomePresenter(this)
    private var recyclerView: RecyclerView? = null
    private var recyclerAdapter: RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_home)
        onInit()
    }

    override fun onInit() {
        initRefresh()
        initToolBar()
        setGreetText()
        homePresenter.refresh()
    }

    private fun initRefresh() {
        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.home_swipeLayout)
        refreshLayout.setColorSchemeResources(R.color.swipeColor)
        refreshLayout.setOnRefreshListener {
            homePresenter.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun initToolBar() {
        val toolbar: Toolbar = findViewById(R.id.home_toolbar)
        toolbar.setOnClickListener {
            recyclerView?.scrollToPosition(0)
            Toast.makeText(this, "回到顶部", Toast.LENGTH_SHORT).show()
        }
        val imageButton: ImageButton = findViewById(R.id.home_tb_button)
        imageButton.setOnClickListener {
            UserActivity.activityStart(this@HomeActivity)
        }
    }

    override fun setGreetText() {
        val calender: Calendar = Calendar.getInstance()
        val month = "第${calender.get(Calendar.MONTH) + 1}月"
        val day = "${calender.get(Calendar.DAY_OF_MONTH)}日"
        val hour: Int = calender.get(Calendar.HOUR_OF_DAY)
        val greedText: TextView = findViewById(R.id.home_tb_greet)
        findViewById<TextView>(R.id.home_tb_day).text = day
        findViewById<TextView>(R.id.home_tb_month).text = month
        when (hour) {
            in 5..9 -> greedText.text = "早上好鸭！"
            in 10..17 -> greedText.text = "干劲十足！"
            in 18..21 -> greedText.text = "别太累了！"
            else -> greedText.text = "早点睡鸭！"
        }
    }

    override fun initRecyclerView(topNewsList: List<TopNewsBean>, newsList: List<NewsBean>) {
        val topAdapter = TopNewsFragmentAdapter(supportFragmentManager)
        for (element in topNewsList) topAdapter.addFragment(TopNewsFragment.newsInstance(element))
        recyclerAdapter = RecyclerAdapter(newsList, topNewsList, topAdapter, this)
        recyclerView = findViewById(R.id.recy_view)
            with(recyclerView!!) {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                        val visibleItemCount = recyclerView.childCount
                        if (((totalItemCount - visibleItemCount) <= firstVisibleItem && homePresenter.isComplete))
                            homePresenter.loadMore()
                    }
                })
        }
    }

    override fun onUpdate() {
        recyclerAdapter!!.notifyDataSetChanged()
    }

    override fun onFailure() {
        val toast = Toast(this)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layoutInflater.inflate(R.layout.toast_error, findViewById(R.id.toast))
        toast.show()
    }
}
