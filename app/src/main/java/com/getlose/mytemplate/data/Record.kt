package com.getlose.mytemplate.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity標示用來存取的物件
//這裡面至少要有一個變數當作 PrimaryKey 用來當作每一個物件的唯一識別，
//並可透過 autoGenerate 來決定是否自動生成 id。
@Entity
class Record(
        @NonNull
        @ColumnInfo(name="nick")
        val nickname:String,
        @NonNull
        val counter:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}