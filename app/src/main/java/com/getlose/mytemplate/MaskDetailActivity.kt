package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.getlose.mytemplate.Model.Feature
import com.getlose.mytemplate.databinding.ActivityMaskDetailBinding

class MaskDetailActivity : AppCompatActivity() {

    val TAG = MaskDetailActivity::class.java.simpleName
    private lateinit var binding: ActivityMaskDetailBinding
    //接收物件
    //as?資料不為空才進行轉換
    private val feature by lazy { intent.getParcelableExtra("feature") as? Feature }
    private val name by lazy { feature?.properties?.name }
    private val maskAdultAmount by lazy { feature?.properties?.mask_adult }
    private val maskChildAmount by lazy { feature?.properties?.mask_child }
    private val phone by lazy { feature?.properties?.phone }
    private val address by lazy { feature?.properties?.address }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: ${ feature?.properties?.name}")
        initView()

    }

    private fun initView() {
        binding.tvName.text = name ?: "資料發生錯誤"
        binding.tvAdultAmount.text = maskAdultAmount.toString()
        binding.tvChildAmount.text = maskChildAmount.toString()
        binding.tvPhone.text = phone
        binding.tvAddress.text = address
    }
}