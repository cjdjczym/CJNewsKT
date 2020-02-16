package com.example.cjnewskt.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.cjnewskt.R
import com.example.cjnewskt.news.NewsActivity
import kotlinx.coroutines.*

/**
 * 自定义RecyclerAdapter构造函数
 * @param newsList    普通新闻集合
 * @param topNewsList 顶部新闻集合
 * @param adapter     顶部新闻fragment的adapter，用于加载viewPager轮播图
 * @param context     活动上下文
 */

class RecyclerAdapter(
    private val newsList: List<NewsBean>,
    private val topNewsList: List<TopNewsBean>,
    private val adapter: TopNewsFragmentAdapter,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val ITEM_TYPE_IMAGE: Int = 0
        const val ITEM_TYPE_NEWS: Int = 1
        const val ITEM_TYPE_LINE: Int = 2
        const val SLEEP_TIME: Long = 4000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_IMAGE ->
                return PicVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.home_recy_vp, parent, false)
                )
            ITEM_TYPE_NEWS -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recy_item, parent, false)
                val holder = NewsVH(view)
                holder.view.setOnClickListener {
                    NewsActivity.activityStart(context, holder.adapterPosition - 1, false)
                }
                return holder
            }
            //ITEM_TYPE_LINE
            else ->
                return LineVH(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.home_recy_dateline, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PicVH -> {
                holder.viewPager.adapter = adapter
                holder.viewPager.currentItem = topNewsList.size * 3
            }
            is NewsVH -> {
                holder.titleView.text = newsList[position - 1].title
                holder.hintView.text = newsList[position - 1].hint
                newsList[0].hint?.let {
                    Glide.with(context)
                        .load(newsList[position - 1].image)
                        .into(holder.imageView)
                }
            }
            is LineVH -> holder.textView.text = newsList[position - 1].title
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        position == 0 -> ITEM_TYPE_IMAGE
        newsList[position - 1].hint == null -> ITEM_TYPE_LINE
        else -> ITEM_TYPE_NEWS
    }

    override fun getItemCount() = newsList.size + 1

    inner class PicVH(view: View) : RecyclerView.ViewHolder(view) {
        val viewPager: ViewPager = view.findViewById(R.id.home_recy_vp)
        private var isTouching = false
        private var isRunning = false //确保只有一个协程在运行

        init {
            viewPagerOnTouch()
            autoScroll()
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun viewPagerOnTouch() {
            viewPager.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> isTouching = true
                    MotionEvent.ACTION_UP -> {
                        isTouching = false
                        if (!isRunning) autoScroll()
                    }
                }
                false
            }
        }

        private fun autoScroll() {
            GlobalScope.launch(Dispatchers.Main) {
                isRunning = true
                //防止立即翻页
                delay(SLEEP_TIME)
                while (!isTouching) {
                    viewPager.currentItem++
                    delay(SLEEP_TIME)
                }
                isRunning = false
            }
        }
    }

    inner class NewsVH(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.recy_card_text)
        val hintView: TextView = view.findViewById(R.id.recy_card_hint)
        val imageView: ImageView = view.findViewById(R.id.recy_card_image)
    }

    inner class LineVH(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.home_recy_date_line)
    }
}