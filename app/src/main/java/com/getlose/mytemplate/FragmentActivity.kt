package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.getlose.mytemplate.Fragment.NewsFragment
import com.getlose.mytemplate.data.RoomDbHelper

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fragmentTrasaction = supportFragmentManager.beginTransaction()
        fragmentTrasaction.add(R.id.container,NewsFragment.instance)
        fragmentTrasaction.commit()
    }
}