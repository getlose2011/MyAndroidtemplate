package com.getlose.mytemplate.library

import android.os.Handler
import android.os.Looper

object Generic {
    /**
     * 通過主執行緒(UI thread)執行動作
     *
     * @param runnable 要執行動作的 Runnable 物件
     */
    fun execOnMainThread(runnable: Runnable) {
        Handler(
            if (Looper.myLooper() != Looper.getMainLooper())
                Looper.getMainLooper()!!
            else
                Looper.myLooper()!!
        ).post(runnable)
    }

}