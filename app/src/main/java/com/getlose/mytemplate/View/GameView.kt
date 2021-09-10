package com.getlose.mytemplate.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.getlose.mytemplate.Infrastructure.Project
import com.getlose.mytemplate.ViewModel.Position


class GameView(context: Context,attrs: AttributeSet) : View(context,attrs) {

    //tag string
    val TAG = GameView::class.java.simpleName
    var size = 0
    private val paint = Paint().apply { color = Color.BLUE}
    private val applePaint = Paint().apply { color = Color.RED}
    var snakeBody : List<Position>? = null
    var apple : Position? = null
    val gap = 3

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

         canvas?.run {
             Log.d(TAG, "onDraw:")
             apple?.run {
                 drawRect(
                         ((this.posX)*size).toFloat()+gap,
                         ((this.posY)*size).toFloat()+gap,
                         ((this.posX+1)*size).toFloat()-gap,
                         ((this.posY+1)*size).toFloat()-gap,
                                 applePaint)
             }

             snakeBody?.forEach {
                 drawRect(
                             (it.posX*size).toFloat()+gap,
                             (it.posY*size).toFloat()+gap,
                         ((it.posX+1)*size).toFloat()-gap,
                 ((it.posY+1)*size).toFloat()-gap,
                         paint)
             }
         }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        size = width/ Project.SnakeViewSize
        Log.d(TAG, "onLayout: size: ${size}, width: ${width}")
    }

}
