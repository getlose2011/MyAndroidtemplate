package com.getlose.mytemplate.Service

import android.app.IntentService
import android.content.Intent
import android.util.Log

//IntentService 特色
//1.會自己destroy
//2.依序執行
//3.IntentService裡的onHandleIntent方法為非ui執行緒

class CacheIntentService : IntentService("IntentService") {

    private val TAG = CacheIntentService::class.java.simpleName

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: ")
        Thread.sleep(5000)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

}