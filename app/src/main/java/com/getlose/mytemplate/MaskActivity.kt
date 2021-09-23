package com.getlose.mytemplate

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.MaskAdapter
import com.getlose.mytemplate.Infrastructure.CountyUtil
import com.getlose.mytemplate.Model.Feature
import com.getlose.mytemplate.ViewModel.MaskViewModel
import com.getlose.mytemplate.databinding.ActivityMaskBinding

class MaskActivity : AppCompatActivity(), MaskAdapter.IMaskItemClickListener {

    val TAG = MaskActivity::class.java.simpleName
    //viewmodel
    private lateinit var viewModel: MaskViewModel
    private lateinit var binding: ActivityMaskBinding
    private lateinit var recyclerAdapter: MaskAdapter
    //預設城市
    private var currentCounty: String = "臺東縣"
    //預設鄉鎮
    private var currentTown: String = "池上鄉"
    //從viewModel取得口罩資料
    private var featuresData : List<Feature>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewmodel
        viewModel = ViewModelProvider(this).get(MaskViewModel::class.java)
        //listen mask data
        viewModel.featuresData.observe(this, { features ->
            runOnUiThread {
                featuresData = features
                setRecyclerData()
            }
        })

        initView()

        getData()
    }

    //初始化畫面
    private fun initView() {
        //recycler
        recyclerAdapter = MaskAdapter(this)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = recyclerAdapter

        //spinner county
        var countyAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, CountyUtil.getAllCountiesName())
        //county 分隔線
        countyAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.spinnerCounty.adapter = countyAdapter
        //county 監聽
        binding.spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCounty = binding.spinnerCounty.selectedItem.toString()
                setSpinnerTown()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //town listener
        binding.spinnerTown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                currentTown = binding.spinnerTown.selectedItem.toString()
                setRecyclerData()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        setDefaultCountyWithTown()
    }

    //設定預設城市
    private fun setDefaultCountyWithTown() {
        binding.spinnerCounty.setSelection(CountyUtil.getCountyIndexByName(currentCounty))
        setSpinnerTown()
    }

    //設定town spinner及鄉鎮
    private fun setSpinnerTown() {
        var townAdapter = ArrayAdapter(
                        this,
                        R.layout.simple_spinner_dropdown_item,
                        CountyUtil.getTownsByCountyName(currentCounty))
        townAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        binding.spinnerTown.adapter = townAdapter
        binding.spinnerTown.setSelection(CountyUtil.getTownIndexByName(currentCounty, currentTown))
    }

    //取得資料
    private fun getData() {
        //取得口罩資料
        viewModel.getMaskData(binding.pb)
    }

    //設定recycler view data
    private fun setRecyclerData() {
        if(featuresData != null){
            recyclerAdapter.features = featuresData!!.filter {
                it.properties.county == currentCounty && it.properties.town == currentTown
            }
        }
    }

    //口罩detail
    override fun onItemClickListener(data: Feature) {
        Log.d(TAG, "onItemClickListener: ${data.properties.name}")
        val intent = Intent(this,MaskDetailActivity::class.java)
        //傳送物件
        intent.putExtra("feature",data)
        startActivity(intent)
    }
}