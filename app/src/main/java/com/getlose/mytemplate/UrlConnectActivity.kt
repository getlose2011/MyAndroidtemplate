package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.getlose.mytemplate.Model.SnookerEvent
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.URL
import kotlin.coroutines.CoroutineContext

class UrlConnectActivity : AppCompatActivity(),CoroutineScope{

    val TAG = UrlConnectActivity::class.java.simpleName
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_connect)
        job = Job()

        launch {
            //開啓網路權限<uses-permission android:name="android.permission.INTERNET" />
            //在 AndroidManifext.xml加上android:usesCleartextTraffic="true",
            //即可訪問http網站
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            val json = JSONArray(data)
            Log.d(TAG, "array length: ${json.length()}")
            for (i in 0..json.length() - 1) {
                //還未用gson外掛前
                //val obj = json.getJSONObject(i)
                //Log.d(TAG, "obj: ${obj.getInt("ID")}")

                //gson
                val result = Gson().fromJson(data, SnookerEvent::class.java)
                result.forEach {
                    Log.d(TAG, "snooker data=> ${it}")
                }
            }
        }
        //網路秏時工作要在非UI執行緒執行
//        Thread{
//            //開啓網路權限<uses-permission android:name="android.permission.INTERNET" />
//            //在 AndroidManifext.xml加上android:usesCleartextTraffic="true",
//            //即可訪問http網站
//            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
//            val json = JSONArray(data)
//            Log.d(TAG, "array length: ${json.length()}")
//            for (i in 0..json.length()-1){
//                //還未用gson外掛前
//                //val obj = json.getJSONObject(i)
//                //Log.d(TAG, "obj: ${obj.getInt("ID")}")
//
//                //gson
//                val result = Gson().fromJson(data,SnookerEvent::class.java)
//                result.forEach {
//                    Log.d(TAG, "snooker data=> ${it}")
//                }
//            }
//            //Log.d(TAG, "URL: $data")
//        }.start()

    }

}