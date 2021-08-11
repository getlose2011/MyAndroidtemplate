package com.getlose.mytemplate

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
            setResult(Activity.RESULT_OK)

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