package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.MovieAdapter
import com.getlose.mytemplate.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_view_model.*

//ViewModel本身具有生命週期
//ViewModel是屬於Android Jetpack裡的lifecycle類，可以有效的解決記憶體洩漏，及難以處理的Activity生命週期問題。
//可參考https://ithelp.ithome.com.tw/articles/10224442
class ViewModelActivity : AppCompatActivity()  {

    val TAG = ViewModelActivity::class.java.simpleName
    private lateinit var viewModel:MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        //ViewModel 在創建時，都是透過 ViewModelProviders 這個來生成
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        //監聽ViewModel裡的Observer變數
        viewModel.movie.observe(this, {
            Log.d(TAG, "onCreate: viewModel.movie")

            adapter = MovieAdapter(it)
            recycler.adapter = adapter//MovieAdapter(result.results)
            adapter.notifyDataSetChanged()

        })

        btn_popular.setOnClickListener {
            Log.d(TAG, "btn_popular")
            viewModel.getData("https://api.themoviedb.org/3/movie/popular?api_key=33ad9f87ec7be68f26f525aab89143eb&language=zh-TW&page=1")
        }

        btn_rank.setOnClickListener {
            Log.d(TAG, "btn_rank")
            //https://www.themoviedb.org/settings/api?language=id
            viewModel.getData("https://api.themoviedb.org/3/movie/top_rated?api_key=33ad9f87ec7be68f26f525aab89143eb&language=zh-TW&page=1")
        }
    }

}