package com.getlose.mytemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.getlose.mytemplate.ViewModel.Dirction
import com.getlose.mytemplate.ViewModel.GameState
import com.getlose.mytemplate.ViewModel.SnakeGameViewModel
import kotlinx.android.synthetic.main.activity_snake.*

class SnakeActivity : AppCompatActivity() {

    private lateinit var viewModel: SnakeGameViewModel
    private val TAG = SnakeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake)

        viewModel = ViewModelProvider(this).get(SnakeGameViewModel::class.java)
        //遊戲狀態
        viewModel.game_status.observe(this, { game_state->
            //遊戲結束
            if (GameState.GAME_OVER == game_state) {
                AlertDialog.Builder(this)
                        .setTitle("info")
                        .setMessage("game over")
                        //.setPositiveButton("重新玩", null)
                        .setPositiveButton("重新玩"){
                            // 此為 Lambda 寫法
                                dialog, which -> viewModel.reset()
                        }
                        .show()
            }
        })

        //分數
        viewModel.score.observe(this,{
            score.setText(it.toString())
        })

        //整個蛇
        viewModel.body.observe(this, {
            Log.d(TAG, "onCreate: body")
            game_view.snakeBody = it
            game_view.invalidate()
        })

        //蘋果
        viewModel.apple.observe(this,{
            game_view.apple = it
            game_view.invalidate()
        })

        //開始遊戲
        viewModel.start()

        up.setOnClickListener{viewModel.move(Dirction.UP)}
        down.setOnClickListener{viewModel.move(Dirction.DOWN)}
        right.setOnClickListener{viewModel.move(Dirction.RIGHT)}
        left.setOnClickListener{viewModel.move(Dirction.LEFT)}

    }
}