package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import java.net.URL

class UrlConnectActivity : AppCompatActivity() {

    val TAG = UrlConnectActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_connect)

        //網路秏時工作要在非UI執行緒執行
        Thread{
            //開啓網路權限<uses-permission android:name="android.permission.INTERNET" />
            //在 AndroidManifext.xml加上android:usesCleartextTraffic="true",
            //即可訪問http網站
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()
            val json = JSONArray(data)
            Log.d(TAG, "array length: ${json.length()}")
            for (i in 0..json.length()-1){
                val obj = json.getJSONObject(i)
                Log.d(TAG, "obj: ${obj.getInt("ID")}")
            }
            //Log.d(TAG, "URL: $data")
        }.start()

    }
}