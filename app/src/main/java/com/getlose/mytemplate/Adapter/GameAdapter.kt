package com.getlose.mytemplate.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getlose.mytemplate.R
import com.getlose.mytemplate.data.Record
import kotlinx.android.synthetic.main.row_game.view.*

class GameAdapter(var context: Context) : RecyclerView.Adapter<GameHolder>(){

    val TAG = GameAdapter::class.java.canonicalName
    var records: List<Record>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_game,parent,false)
        return GameHolder(view)
    }

    override fun getItemCount(): Int {
        return records?.count()!!
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.txtNick.text = records?.get(position)?.nickname
        holder.txtCounter.text = records?.get(position)?.counter.toString()
    }

    fun update(record: List<Record>?) {
        records = record
        notifyDataSetChanged()
    }

}

class GameHolder(view: View): RecyclerView.ViewHolder(view){
    val txtNick = view.tx_nick
    val txtCounter = view.tx_counter
}