package com.getlose.mytemplate

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.getlose.mytemplate.databinding.ActivityContentProviderBinding

class ContentProviderActivity : AppCompatActivity() {
    val TAG = ContentProviderActivity::class.java.simpleName
    val RC_REQUEST = 100
    val REQUEST_ENABLE_GPS = 101
    private lateinit var binding: ActivityContentProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //獲取位置權限,位置權限有兩種，
    // 1.概略位置存取權(ACCESS_COARSE_LOCATION)
    // 2.精確位置存取(ACCESS_FINE_LOCATION)
    fun getAccessFineLocationPermission(view: View) {
        //先取得用戶APP是否有給予以權限
        var permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if(permission == PackageManager.PERMISSION_GRANTED){
            checkGPSState()
        }else{
            //androidmanifest.xml 要加權限
            //手機會跳出權限詢問視窗
            //要求用戶給我們APP使用權限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),RC_REQUEST)
        }
    }

    //獲取讀取聯絡人權限
    fun getReadContactsPermission(view: View) {
        //先取得用戶是否有給予以權限
        var permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)

        if(permission == PackageManager.PERMISSION_GRANTED){
            readContacts()
        }else{
            //androidmanifest.xml 要加權限
            //手機會跳出權限詢問視窗
            //要求用戶給我們APP使用權限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),RC_REQUEST)
        }
    }

    //讀取聯絡人資料
    private fun readContacts() {
        val cursor =
                contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (cursor?.moveToNext()!!) {
            val name =
                    cursor?.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            Log.d(TAG, "name=>$name")
        }
    }

    //查GPS是否開啟　
    private fun checkGPSState() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(this)
                    .setTitle("GPS 尚未開啟")
                    .setMessage("使用此功能需要開啟 GSP 定位功能")
                    .setPositiveButton("前往開啟",
                            DialogInterface.OnClickListener { _, _ ->
                                startActivityForResult(
                                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS
                                )
                            })
                    .setNegativeButton("取消", null)
                    .show()
        } else {
            Toast.makeText(this, "已獲取到位置權限且GPS已開啟，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
        }
    }

    //callback 當用戶在危險權限選擇是還和否時
    //覆寫此方法，要求權限後，會收到用戶決定是否給權限的結果 CallBack
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if(requestCode == RC_REQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION){
                    Toast.makeText(this,"ACCESS_FINE_LOCATION",Toast.LENGTH_LONG).show()
                }else if(permissions[0] == Manifest.permission.READ_CONTACTS){
                    Toast.makeText(this,"READ_CONTACTS",Toast.LENGTH_LONG).show()
                    readContacts()
                }
            }else{
                if(permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION){
                    Toast.makeText(this,"deny ACCESS_FINE_LOCATION",Toast.LENGTH_LONG).show()
                    checkGPSState()
                }
            }
        }
    }

    //startActivityForResult callback
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ENABLE_GPS -> {
                checkGPSState()
            }
        }
    }
}