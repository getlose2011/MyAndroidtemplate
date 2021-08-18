package com.getlose.mytemplate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.getlose.mytemplate.*
import com.getlose.mytemplate.Model.Movie
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieAdapter(var movies:List<Movie>?) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    val TAG = MovieAdapter::class.java.simpleName

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view){
        val title = view.txt_title
        val poster = view.ig_poster
        val score = view.txt_score
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_movie,parent,false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int {
        return movies?.size?:0
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.title.setText(movies?.get(position)?.title)
        holder.score.setText(movies?.get(position)?.vote_count.toString())
        holder.poster.load("https://image.tmdb.org/t/p/w500${movies?.get(position)?.poster_path}")
        {
            placeholder(R.drawable.picture)
//            crossfade(true)
//            transformations(CircleCropTransformation())
        }

    }

}