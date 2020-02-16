package com.example.cjnewskt.home

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cjnewskt.R
import com.example.cjnewskt.news.NewsActivity

class TopNewsFragment : Fragment() {
    companion object {
        fun newsInstance(bean: TopNewsBean): TopNewsFragment {
            val fragment = TopNewsFragment()
            val args = Bundle()
            args.putSerializable("bean", bean)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_fragment_top, container, false)
        val titleText: TextView = view.findViewById(R.id.home_recy_vp_title)
        val hintText: TextView = view.findViewById(R.id.home_recy_vp_hint)
        val imageView: ImageView = view.findViewById(R.id.home_recy_vp_image)
        val bean = arguments!!.getSerializable("bean") as TopNewsBean
        titleText.text = bean.title
        hintText.text = bean.hint
        imageView.setOnClickListener {
            NewsActivity.activityStart(context!!, bean.position, true)
        }
        Glide.with(this).load(bean.image).into(imageView)
        val linearLayout: LinearLayout = view.findViewById(R.id.home_recy_vp_color)
        val colors = intArrayOf(0, -0x1000000)
        val drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
        linearLayout.setBackgroundDrawable(drawable)
        linearLayout.alpha = 0.9f
        return view
    }
}
