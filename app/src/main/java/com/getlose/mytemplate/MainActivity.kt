package com.getlose.mytemplate

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.getlose.mytemplate.Adapter.FunctionAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //tag string
    val TAG = MainActivity::class.java.simpleName
    //判斷使用者登入狀態
    var login = false
    //list for recycle view
    val Functions = listOf<String>(
        "Content Provider & 危險權限",
        "資料儲存 Room用Kotlin Coroutines 協程",
        "網路連線與 JSON 資料解析實務",
        "Camera3",
        "Camera4",
        "Camera5",
        "Camera6",
        "Camera7",
        "Camera8",
        "Camera9",
        "Camera10",
        "Camera11")

    companion object{
        //判斷登入頁面回傳的requestCode
        val RC_LOGIN = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!login){
            goLoginActivity()
        }
    }

    private fun goLoginActivity() {
        Intent(this,LoginActivity::class.java).apply {
            putExtra(getString(R.string.check_login),login)
            startActivityForResult(this, RC_LOGIN)
        }
    }

    //判斷回傳的requestCode和resultCode
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                val login_result = data?.getStringExtra(getString(R.string.login_result))
                Log.d(TAG, "RESULT, $login_result")
                initView()
            }else{
                goLoginActivity()
            }
        }
    }

    //初始畫面
    private fun initView() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter(Functions,this)
    }

    //toolbar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //toolbar menu check selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.antion_call){
            Log.d(TAG, "onOptionsItemSelected: antion_call")
        }
        return super.onOptionsItemSelected(item)
    }
}