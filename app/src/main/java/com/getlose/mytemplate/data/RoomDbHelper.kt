package com.getlose.mytemplate.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//註解為 @Database 的類別必須繼承 RoomDatabase ，並加上需要儲存的 Entity 類別 和 Version，
//這裡用的是 Singleton 寫法，也是官方所推薦的，因為實體的產生很耗資源，而且也不需要多個資料庫實體，所以宣告為 Singleton 即可。
@Database(entities = arrayOf(Record::class),version = 1)
abstract class RoomDbHelper : RoomDatabase(){
    abstract fun RecordDao() : RecordDao

    companion object{
        private var instance : RoomDbHelper? = null
        fun getInstance(context: Context) : RoomDbHelper? {

            if(instance == null){
                instance = Room.databaseBuilder(context, RoomDbHelper::class.java,"game.db")
                    .build()
            }

            return instance
        }
    }

}