import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.getlose.mytemplate.BuildConfig
import com.getlose.mytemplate.library.Generic
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


class OkHttpUtil {

    private var mClient: OkHttpClient? = null
    val TAG = OkHttpUtil::class.java.simpleName

    companion object {
        val mOkHttpUtil: OkHttpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpUtil()
        }

    }

    init {
        mClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BASIC
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
            .build()
    }

    fun getAysnc(url: String, pb: ProgressBar? = null, callback: ICallback){
        //轉圈圈
        Generic.execOnMainThread{
            pb?.visibility = View.VISIBLE
        }

        var request = with(Request.Builder()){
            url(url)
            get()
            build()
        }

        var call = mClient?.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
                Generic.execOnMainThread{
                    pb?.visibility = View.GONE
                }
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(response)
                //轉圈圈

                Generic.execOnMainThread{
                    pb?.visibility = View.GONE
                }
            }
        })
    }

    fun postAysnc(url: String, paramsModel: Any?, callback: ICallback){
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonString = Gson().toJson(paramsModel)
        Log.d(TAG, "postAysnc: ${jsonString}")
        val body = jsonString.toString().toRequestBody(mediaType)

        var request = with(Request.Builder()){
            url(url)
            post(body)
            build()
        }

        var call = mClient?.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(response)
            }
        })
    }

    interface ICallback {
        fun onResponse(response: Response)

        fun onFailure(e: IOException)
    }
}