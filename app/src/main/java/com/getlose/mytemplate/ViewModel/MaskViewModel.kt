package com.getlose.mytemplate.ViewModel

import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getlose.mytemplate.Infrastructure.PHARMACIES_DATA_URL
import com.getlose.mytemplate.MaskActivity
import com.getlose.mytemplate.Model.Feature
import com.getlose.mytemplate.Model.MaskModel
import com.google.gson.Gson
import okhttp3.Response
import java.io.IOException

class MaskViewModel : ViewModel() {
    val TAG = MaskActivity::class.java.simpleName
    lateinit var county : String
    lateinit var town : String
    var featuresData = MutableLiveData<List<Feature>>()
    private var features : List<Feature>? = null

    fun getMaskData(pb: ProgressBar? = null){

        if(features == null){
            OkHttpUtil.mOkHttpUtil.getAysnc(PHARMACIES_DATA_URL,
                    pb,
                    object : OkHttpUtil.ICallback {
                        override fun onResponse(response: Response) {
                            var data = response.body?.string()
                            val result = Gson().fromJson(data, MaskModel::class.java)
                            //featuresData.postValue(result?.features)
                            features = result?.features!!
                            setMaskData()
                        }
                        override fun onFailure(e: IOException) {}
                    }
            )
        }else{
            setMaskData()
        }
    }

    //設定口罩資料
    private fun setMaskData() {
        if(features != null){
            featuresData.postValue(features?.filter {
                it.properties.county == county && it.properties.town == town
            })
        }
    }
}