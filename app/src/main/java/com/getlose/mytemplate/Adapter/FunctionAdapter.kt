package com.getlose.mytemplate.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.getlose.mytemplate.ContentProviderActivity
import com.getlose.mytemplate.R
import com.getlose.mytemplate.RoomActivity
import kotlinx.android.synthetic.main.row_activity_function.view.*

class FunctionAdapter(val Functions: List<String>,var context: Context) : RecyclerView.Adapter<FunctionHolder>() {

    val TAG = FunctionAdapter::class.java.canonicalName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_activity_function,parent,false)
        Log.d(TAG, "FunctionAdapter, onCreateViewHolder")
        return FunctionHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "FunctionAdapter, getItemCount")
        return Functions.size
    }

    override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
        Log.d(TAG, "FunctionAdapter, onBindViewHolder")
        holder.txtName.text = Functions.get(position)
        holder.txtName.setOnClickListener {
            functionClick(position)
        }
    }

    private fun functionClick(position: Int) {
        when(position){
            0->{
                Intent(context, ContentProviderActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
            1->{
                Intent(context, RoomActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
//            2->{
//
//            }
            else->return
        }
        Log.d(TAG, "FunctionAdapter, functionClick: $position")
        Toast.makeText(context,Functions.get(position), Toast.LENGTH_LONG).show()
    }
}

class FunctionHolder(view: View): RecyclerView.ViewHolder(view){
    val txtName = view.name
}