package com.getlose.mytemplate.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getlose.mytemplate.Infrastructure.Project
import kotlin.concurrent.fixedRateTimer

class SnakeGameViewModel : ViewModel(){

    private val TAG = SnakeGameViewModel::class.java.simpleName
    //分數
    var score = MutableLiveData<Int>()
    //身體
    var body = MutableLiveData<List<Position>>()
    //蘋果的位置
    var apple = MutableLiveData<Position>()
    //遊戲狀態
    var game_status = MutableLiveData<GameState>()
    //遊戲分數
    var point = 0
    //蛇的list
    private val snakeBody = mutableListOf<Position>()
    //蘋果
    private lateinit var  applePos : Position
    //預設往左邊走
    private var direction = Dirction.LEFT

    //遊戲開始
    fun start(){
        //預設分數
        score.postValue(point)
        //預設蛇座標
        snakeBody.apply {
                this.add(Position(10,10))
                this.add(Position(11,10))
                this.add(Position(12,10))
                this.add(Position(13,10))
        }.also {
            body.value = it
        }
        //預設開始遊戲狀態
        game_status.value = GameState.GAME_START
        //蘋果座標
        generateApple()
        //開啓定時器
        fixedRateTimer("timer",true,500,500){
            //取得身體第一個位置，並判斷方向為何，有無碰到自己身體
            val pos = snakeBody.first().copy().apply {
                Log.d(TAG, "be pos: ${this.posX}, ${this.posY}")

                snakeBody.forEach {
                    Log.d(TAG, "snakebody foreach pos:=> ${it.posX},${it.posY}")
                }
                when(direction){
                    Dirction.LEFT->posX--
                    Dirction.RIGHT->posX++
                    Dirction.UP->posY--
                    Dirction.DOWN->posY++
                }
                Log.d(TAG, "af pos: ${this.posX}, ${this.posY}")
                //判斷蛇是不是跑出畫面及撞到自己本身
                if(posX < 0 || posY < 0 || posX >= Project.SnakeViewSize  || posY >= Project.SnakeViewSize ||
                        snakeBody.contains(this)
                ){
                    //關掉定時器
                    cancel()
                    game_status.postValue(GameState.GAME_OVER)
                }
            }

            Log.d(TAG, "start: x:${pos.posX},y:${pos.posY}")
            snakeBody.add(0,pos)

            //判斷是否吃到蘋果
            if(pos == applePos){
                generateApple()
                point += 100
            }else{
                snakeBody.removeLast()
            }
            score.postValue(point)
            body.postValue(snakeBody)
        }
    }

    //產生蘋果座標
    fun generateApple(){
        //do {
        //  applePos = Position(Random.nextInt(Project.SnakeViewSize), Random.nextInt(Project.SnakeViewSize))
        //}while (snakeBody.contains(applePos))

        val spots = mutableListOf<Position>().apply {
            for (i in 0..19){
                for (j in 0..19){
                    add(Position(i,j))
                }
            }
        }

        spots.removeAll(snakeBody)
        spots.shuffle()
        applePos = spots[0]

        apple.postValue(applePos)
    }

    //使用者按了方向按鈕
    fun move(direction:Dirction){
        this.direction = direction
    }

    //重玩
    fun reset(){
        point = 0
        snakeBody.clear()
        direction = Dirction.LEFT
        start()
    }

}

//位置
data class Position(var posX:Int, var posY:Int)

//移動
enum class Dirction{
    UP,DOWN,RIGHT,LEFT
}

//遊戲狀態
enum class GameState{
    GAME_OVER,GAME_START
}