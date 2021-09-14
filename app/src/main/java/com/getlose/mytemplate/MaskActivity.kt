package com.getlose.mytemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.getlose.mytemplate.ViewModel.MaskViewModel
import com.getlose.mytemplate.databinding.ActivityMaskBinding

class MaskActivity : AppCompatActivity() {

    val TAG = MaskActivity::class.java.simpleName
    private lateinit var viewModel: MaskViewModel
    private lateinit var binding: ActivityMaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MaskViewModel::class.java)

        viewModel.feature.observe(this, { f ->
            binding.tvFeature.text = f
        })
        //取得口罩資料
        viewModel.getMaskData()

    }
}