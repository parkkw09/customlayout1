package app.peterkwp.customlayout1.filter

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import app.peterkwp.customlayout1.R

class FilterItemView: FrameLayout {

    private val appTag = "FilterItemView"

    lateinit var mTextView: TextView
    var isEnable = false

    constructor(context: Context): super(context) { init(context) }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) { init(context) }
    constructor(context: Context, attrs: AttributeSet, defStyleRes: Int) : super(context, attrs, defStyleRes) { init(context) }

    private fun init(context: Context) {
//        Log.d(appTag, "init()")
        val view = LayoutInflater.from(context).inflate(R.layout.item_filtersticker, this, true)
        mTextView = view.findViewById(R.id.item_button)
    }

    fun setData(value: String) {
//        Log.d(appTag, "setData()[$value]")
        mTextView.text = value
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(appTag, "onDraw()")
    }

    override fun performClick(): Boolean {
        isEnable = !isEnable
        return super.performClick()
    }
}