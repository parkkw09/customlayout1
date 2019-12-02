package app.peterkwp.customlayout1.filter

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import app.peterkwp.customlayout1.App

class FilterView @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet? = null) : View(context, attrs) {

    private var data: ArrayList<String>? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(App.TAG, "onMeasure()")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(App.TAG, "onDraw()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(App.TAG, "onLayout()")
    }

    fun setData(data: List<String>) {
        this.data?.addAll(data)
        invalidate()
    }
}