package ru.lachesis.nasapictures.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class EquilateralImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?=null,
    defStyle: Int=0
): androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyle) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}