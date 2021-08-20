package com.getlose.mytemplate.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.MovieAdapter
import com.getlose.mytemplate.Model.MovieEvent
import com.getlose.mytemplate.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_api.*
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.coroutines.CoroutineContext

class NewsFragment : Fragment(), CoroutineScope {

    val TAG = NewsFragment::class.java.simpleName
    private lateinit var job: Job
    private lateinit var adapter: MovieAdapter

    companion object{        
        val instance : NewsFragment by lazy {
            NewsFragment()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)

        launch {
            val data = URL("https://api.themoviedb.org/3/movie/popular?api_key=33ad9f87ec7be68f26f525aab89143eb&language=zh-TW&page=1")
                .readText()

            val result =  Gson().fromJson(data, MovieEvent::class.java)
            Log.d(TAG, "page: ${result.page}")

            adapter = MovieAdapter(result.results)

            //ui thread
            activity?.runOnUiThread {
                view.recycler.adapter = adapter
                //資料有改變時，呼叫recyclerVview重畫
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Log.d(TAG, "onDestroy: ")
    }
}