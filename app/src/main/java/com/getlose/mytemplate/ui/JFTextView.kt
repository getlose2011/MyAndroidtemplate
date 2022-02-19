package com.getlose.mytemplate.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class JFTextView(context: Context, attrs: AttributeSet): AppCompatTextView(context, attrs) {

    init{
        applyFont()
    }


    //這一行傳入剛剛放在asset的字型
    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets,"jf-openhuninn-1.1.ttf")
        setTypeface(typeface)
    }
}