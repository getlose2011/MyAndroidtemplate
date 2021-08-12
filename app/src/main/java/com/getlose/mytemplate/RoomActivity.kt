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

//可參考https://ithelp.ithome.com.tw/articles/10223511
class RoomActivity : AppCompatActivity() {
    //tag string
    val TAG = RoomActivity::class.java.canonicalName
    private val adapter = GameAdapter(this)
    var dbHelper :RoomDbHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        dbHelper = RoomDbHelper.getInstance(this)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        //非ui執行緒
        Thread(){

            adapter.records = dbHelper?.RecordDao()?.getAll()
            //ui執行緒
            runOnUiThread {
                recycler.adapter = adapter
            }

            dbHelper?.RecordDao()?.getAll()?.forEach{
                Log.d(TAG, "records: nick:${it.nickname}, counter:${it.counter}")
                deleteRecord(it)
            }

        }.start()

        //存檔
        btn_save.setOnClickListener {
            val nick = ed_nick.text.toString()
            val counter = ed_counter.text.toString().toIntOrNull()?:0
            //非ui執行緒
            Thread(){
                dbHelper?.RecordDao()?.insert(Record(nick,counter))

                val records = dbHelper?.RecordDao()?.getAll()

                //ui執行緒
                runOnUiThread {
                    // Stuff that updates the UI
                    updateData(records)
                }

            }.start()
        }

    }

    //更新
    fun updateData(records: List<Record>?){
        adapter.update(records)
    }

    //刪除
    fun deleteRecord(record: Record){
        dbHelper?.RecordDao()?.delete(record)
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

}