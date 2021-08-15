package com.getlose.mytemplate

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ContentProviderActivity : AppCompatActivity() {
    val TAG = ContentProviderActivity::class.java.simpleName
    val RC_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)

        //先取得用戶是否有給予以權限
        var permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)

        if(permission == PackageManager.PERMISSION_GRANTED){
            readContact()
        }else{
            //androidmanifest.xml 要加權限
            //手機會跳出權限詢問視窗
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),RC_REQUEST)
        }
    }

    //callback 當用戶在危險權限選擇是還和否時
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == RC_REQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)readContact()
        }
    }

    //讀取聯絡人資料
    private fun readContact() {
        val cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (cursor?.moveToNext()!!) {
            val name =
                cursor?.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            Log.d(TAG, "name=>$name")
        }
    }
}