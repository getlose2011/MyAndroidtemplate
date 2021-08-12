package com.getlose.mytemplate.data

import androidx.room.*

//CRUD 增刪查改都是透過這個介面來完成
@Dao
interface RecordDao {

    //表示新增物件時和舊物件發生衝突後的處置
    //a.REPLACE 蓋掉
    //b.IGNORE 忽略，還是舊的資料
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record:Record)

    @Query("select * from record order by id Desc")
    fun getAll() : List<Record>

    @Query("select * from record WHERE id = :id")
    fun getOne(id: Long) : Record

    //
    @Delete
    fun delete(record:Record)
}