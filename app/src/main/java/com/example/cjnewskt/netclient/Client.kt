package com.example.cjnewskt.netclient

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private var mClient: OkHttpClient? = null
    fun sendOKHttpRequest(address: String, callback: okhttp3.Callback) {
        mClient = mClient ?: OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(address)
            .get()
            .build()
        mClient!!.newCall(request).enqueue(callback)
    }

    inline fun <reified T> invoke(json: String): T = Gson().fromJson(json, T::class.java)
}