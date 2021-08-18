package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_component.*

class ComponentActivity : AppCompatActivity() {
    
    val TAG = ComponentActivity::class.java.simpleName
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_component)

        val colors = arrayOf("red","blue","dark")
        val adapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,colors)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "onItemSelected: ${colors.get(position)}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

}