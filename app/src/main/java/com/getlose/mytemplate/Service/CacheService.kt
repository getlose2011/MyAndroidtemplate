package com.getlose.mytemplate.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

//Service是在背景運行並可與系統互動的元件，
//他不能自己運作，需要通過某一個Activity來執行
//Service預設是在主線程執行，所以若要執行消耗時間的工作務必用Thread等方式到背景線呈執行，以免造成畫面卡頓。
class CacheService() : Service() {

    private val TAG = CacheService::class.java.simpleName
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        return START_STICKY //被停止後再次啟動，但Intent會被清空
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}