package com.example.cjnewskt.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cjnewskt.R

class UserActivity : AppCompatActivity() {

    companion object {
        private var isLight = true
        fun activityStart(context: Context) {
            val intent = Intent(context, UserActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_user)
        initButtons()
    }

    private fun initButtons() {
        val imageButton: ImageButton = findViewById(R.id.user_tb_back)
        imageButton.setOnClickListener {
            onBackPressed()
        }
        val themeButton: ImageButton = findViewById(R.id.user_theme)
        themeButton.setBackgroundResource(R.drawable.daynight)
        themeButton.setOnClickListener {
            if (isLight) {
                isLight = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                recreate()
            } else {
                isLight = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }
        }
    }
}