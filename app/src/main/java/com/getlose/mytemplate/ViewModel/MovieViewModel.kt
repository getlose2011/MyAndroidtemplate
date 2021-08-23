package com.getlose.mytemplate.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getlose.mytemplate.Model.Movie
import com.getlose.mytemplate.Model.MovieEvent
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

//MovieViewModel繼承viewModel
class MovieViewModel : ViewModel() {

    var movie = MutableLiveData<List<Movie>>()

    fun getData(url: String){
        viewModelScope.launch(Dispatchers.IO) {
            val data = URL(url).readText()
            //用value給值後，會通知observe
            val result = Gson().fromJson(data, MovieEvent::class.java)
            movie.postValue(result.results)
        }
    }

}