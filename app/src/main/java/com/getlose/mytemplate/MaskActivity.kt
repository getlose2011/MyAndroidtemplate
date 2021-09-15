package com.getlose.mytemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.MaskAdapter
import com.getlose.mytemplate.ViewModel.MaskViewModel
import com.getlose.mytemplate.databinding.ActivityMaskBinding

class MaskActivity : AppCompatActivity() {

    val TAG = MaskActivity::class.java.simpleName
    private lateinit var viewModel: MaskViewModel
    private lateinit var binding: ActivityMaskBinding
    private lateinit var adapter: MaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewmodel
        viewModel = ViewModelProvider(this).get(MaskViewModel::class.java)
        //listen mask data
        viewModel.features.observe(this, { data ->
            runOnUiThread {
                adapter.features = data
            }
        })

        initView()

        getData()

    }

    //初始化畫面
    private fun initView() {
        adapter = MaskAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
        //設定項目分隔線 (divider)
        binding.recycler.addItemDecoration(
                DividerItemDecoration(
                        this@MaskActivity,
                        DividerItemDecoration.VERTICAL
                )
        )
    }

    //取得資料
    private fun getData() {
        //取得口罩資料
        viewModel.getMaskData(binding.pb)
    }

}