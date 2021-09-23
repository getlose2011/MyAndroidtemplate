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
    var featuresData = MutableLiveData<List<Feature>>()

    fun getMaskData(pb: ProgressBar? = null){

        OkHttpUtil.mOkHttpUtil.getAysnc(PHARMACIES_DATA_URL,
            pb,
            object : OkHttpUtil.ICallback {
                override fun onResponse(response: Response) {
                    var data = response.body?.string()
                    val result = Gson().fromJson(data, MaskModel::class.java)
                    featuresData.postValue(result?.features)
                }
                override fun onFailure(e: IOException) {}
            }
        )
    }
}