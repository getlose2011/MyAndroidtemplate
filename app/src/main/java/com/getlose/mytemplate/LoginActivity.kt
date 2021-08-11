package com.getlose.mytemplate

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    //tag string
    val TAG = MainActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val check_login = intent.getBooleanExtra(getString(R.string.check_login),false)
        Log.d(TAG, "getString(R.string.check_login): $check_login")


        val user = getSharedPreferences("atm", Context.MODE_PRIVATE)
            .getString("PREF_USERID","")
        val password = getSharedPreferences("atm", Context.MODE_PRIVATE)
            .getString("PREF_PASSWORD","")

        ed_userid.setText(user)
        ed_passwd.setText(password)
    }

    fun login(view: View){
        //直接取得頁面的id要在id 'kotlin-android-extensions'
        val user = ed_userid.text.toString()
        val password = ed_passwd.text.toString()

        if(user == "jack" && password == "1234"){
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show()
            val intent = Intent()
            intent.putExtra(getString(R.string.login_result),"ok")
            setResult(Activity.RESULT_OK,intent)

            //記憶使用者帳密
            getSharedPreferences("atm", Context.MODE_PRIVATE)
                .edit()
                .putString("PREF_USERID",user)
                .putString("PREF_PASSWORD",password)
                .apply()

            finish()
        } else {
            AlertDialog.Builder(this)
                .setMessage("登入失敗")
                .setPositiveButton("OK", null)
                .show()
        }

    }

    fun cancel(view: View){

    }

}