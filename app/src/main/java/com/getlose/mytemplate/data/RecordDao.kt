package com.getlose.mytemplate.data

import androidx.room.*

//CRUD 增刪查改都是透過這個介面來完成
@Dao
interface RecordDao {

    //function前加上suspend是給coroutines用的 , 執行到這個 function 時是可以被 suspend (暫停) ,
    //放出 UI thread 去執行其他的事情，等到 IO thread 把需要花很多時間的完成後再報執行
    //必須要在 coroutineScope 的 launch.
    //表示新增物件時和舊物件發生衝突後的處置
    //a.REPLACE 蓋掉
    //b.IGNORE 忽略，還是舊的資料
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record:Record)

    @Query("select * from record order by id Desc")
    suspend fun getAll() : List<Record>

    @Query("select * from record WHERE id = :id")
    suspend fun getOne(id: Long) : Record

    //
    @Delete
    suspend fun delete(record:Record)
}