package com.getlose.mytemplate.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.getlose.mytemplate.*
import kotlinx.android.synthetic.main.row_function.view.*

class FunctionAdapter(val Functions: List<String>,var context: Context) : RecyclerView.Adapter<FunctionHolder>() {

    val TAG = FunctionAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_function,parent,false)
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
            2->{
                Intent(context, UrlConnectActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
            3->{
                Intent(context, ApiActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
            4-> {
                Intent(context, ComponentActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
            5->{
                Intent(context, FragmentActivity::class.java).apply {
                        context.startActivity(this)
                    }
            }
            6-> {
                Intent(context, ViewModelActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
            7-> {
                Intent(context, SnakeActivity::class.java).apply {
                    context.startActivity(this)
                }
            }
            8-> {
            Intent(context, MaskActivity::class.java).apply {
                context.startActivity(this)
            }
        }
            else->return
        }
        Log.d(TAG, "FunctionAdapter, functionClick: $position")
        Toast.makeText(context,Functions.get(position), Toast.LENGTH_LONG).show()
    }
}

class FunctionHolder(view: View): RecyclerView.ViewHolder(view){
    val txtName = view.name
}