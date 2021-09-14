package com.getlose.mytemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.getlose.mytemplate.ViewModel.MaskViewModel
import kotlinx.android.synthetic.main.activity_mask.*

class MaskActivity : AppCompatActivity() {

    val TAG = MaskActivity::class.java.simpleName
    private lateinit var viewModel: MaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mask)

        viewModel = ViewModelProvider(this).get(MaskViewModel::class.java)

        viewModel.feature.observe(this,{f->
            tv_feature.setText(f)
        })
        viewModel.body.observe(this,{b->
            tv_user_boday.setText(b)
        })
        viewModel.getMaskData()
        viewModel.getUserData()
    }
}