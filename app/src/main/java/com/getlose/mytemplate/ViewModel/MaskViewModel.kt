package com.getlose.mytemplate.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getlose.mytemplate.BuildConfig
import com.getlose.mytemplate.MaskActivity
import com.getlose.mytemplate.Model.MaskModel
import com.getlose.mytemplate.Model.Request.RequestApiModel
import com.google.gson.Gson
import okhttp3.Response
import java.io.IOException

class MaskViewModel : ViewModel() {
    val TAG = MaskActivity::class.java.simpleName
    var feature = MutableLiveData<String>()
    var body = MutableLiveData<String>()

    private companion object {
        const val REQUEST_FLASH_LIST = "flash_list"
    }

    fun getMaskData(){

        OkHttpUtil.mOkHttpUtil.getAysnc("https://raw.githubusercontent.com/thishkt/pharmacies/master/data/info.json",
                object : OkHttpUtil.ICallback {
                    override fun onResponse(response: Response) {
                        var data = response.body?.string()
                        val result = Gson().fromJson(data, MaskModel::class.java)
                        Log.d(TAG, "onResponse: ${result.type}")
                        Log.d(TAG, "BuildConfig.DEBUG: ${BuildConfig.DEBUG}")
                        feature.postValue(result?.type)
                    }

                    override fun onFailure(e: IOException) {
                        TODO("Not yet implemented")
                    }

                }
        )
    }

    fun getUserData(){

        OkHttpUtil.mOkHttpUtil.postAysnc(
                "http://10.3.109.72:56874/api/WebAPI/admin_get_test",
                    RequestApiModel().apply {
                        request = REQUEST_FLASH_LIST
                        param = RequestApiModel.RequestApiParamModel().apply{
                            email = "ggg@sss.com"
                        }
                    },
                    object : OkHttpUtil.ICallback {
                        override fun onResponse(response: Response) {
                            var data = response.body?.string()
                            //val result = Gson().fromJson(data, UserModel::class.java)
                            Log.d(TAG, "onResponse: ${data}")
                            //body.postValue(result?.body)
                        }

                        override fun onFailure(e: IOException) {
                            TODO("Not yet implemented")
                        }

                    }
        )
    }

}