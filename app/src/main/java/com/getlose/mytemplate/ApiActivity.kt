package com.getlose.mytemplate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.MovieAdapter
import com.getlose.mytemplate.Model.MovieEvent
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext

class ApiActivity : AppCompatActivity() ,CoroutineScope{

    val TAG = ApiActivity::class.java.simpleName
    private lateinit var job: Job
    private lateinit var adapter: MovieAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        job = Job()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        launch {
            val data = URL("https://api.themoviedb.org/3/movie/popular?api_key=33ad9f87ec7be68f26f525aab89143eb&language=zh-TW&page=1")
                    .readText()

            val result =  Gson().fromJson(data, MovieEvent::class.java)
            Log.d(TAG, "page: ${result.page}")

            adapter = MovieAdapter(result.results)

            //ui thread
            runOnUiThread {
                recycler.adapter = adapter//MovieAdapter(result.results)
                //資料有改變時，呼叫recyclerVview重畫
                adapter.notifyDataSetChanged()
            }
        }

//        Thread{
//            val data = URL("https://api.themoviedb.org/3/movie/popular?api_key=33ad9f87ec7be68f26f525aab89143eb&language=zh-TW&page=1")
//                    .readText()
//            Log.d(TAG, "data: ${data}")
//        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}