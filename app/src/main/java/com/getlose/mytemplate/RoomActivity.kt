package com.getlose.mytemplate

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.GameAdapter
import com.getlose.mytemplate.data.Record
import com.getlose.mytemplate.data.RoomDbHelper
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

//可參考https://ithelp.ithome.com.tw/articles/10223511
class RoomActivity : AppCompatActivity(),CoroutineScope{
    //tag string
    val TAG = RoomActivity::class.java.simpleName
    private lateinit var job:Job
    private val adapter = GameAdapter(this)
    var dbHelper :RoomDbHelper? = null

    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main//launch 的程序跑在 UI thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        job = Job()

        dbHelper = RoomDbHelper.getInstance(this)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        launch {
            adapter.records = dbHelper?.RecordDao()?.getAll()
            recycler.adapter = adapter
            dbHelper?.RecordDao()?.getAll()?.forEach{
                Log.d(TAG, "records: nick:${it.nickname}, counter:${it.counter}")
                dbHelper?.RecordDao()?.delete(it)
            }
        }

        //存檔
        btn_save.setOnClickListener {
            val nick = ed_nick.text.toString()
            val counter = ed_counter.text.toString().toIntOrNull()?:0
            launch {
                dbHelper?.RecordDao()?.insert(Record(nick,counter))
                val records = dbHelper?.RecordDao()?.getAll()
                updateData(records)
            }
        }
    }

    //更新
    fun updateData(records: List<Record>?){
        adapter.update(records)
    }

    //提示
    fun info(view: View){
        AlertDialog.Builder(this)
            .setTitle("Room 由三個部分組成 : Entity、DAO 和 Database")
            .setMessage("1.Entity 建立物件" +
                        "2.Dao 介面(新增、修改、刪除、查詢...方法)" +
                        "3.Database 抽象(操作Dao物件)")
            .setPositiveButton("ok",null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //可必避使用者在這個activity時,馬上按了返回鍵,也停止Coroutines
        job.cancel()
    }

}