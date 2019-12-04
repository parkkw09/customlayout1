package app.peterkwp.customlayout1.filter

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import app.peterkwp.customlayout1.calculateSize
import app.peterkwp.customlayout1.toDp
import java.io.Serializable

class FilterView: ViewGroup {

    private val appTag = "FilterView"

    private var mData: ArrayList<String>? = null
    private var mSize: ArrayList<Coordinate>? = null
    private var mPrevX: Int = 0
    private var mPrevY: Int = 0
    private var margin = 6.toDp
    private var mPrevHeight = 0
    private var mPrevItem: View? = null
    private var mListener: FilterItemListener? = null

    constructor(context: Context): super(context)
    constructor(context: Context,
                attrs: AttributeSet): super(context, attrs)
    constructor(context: Context,
                attrs: AttributeSet,
                defStyleRes: Int) : super(context, attrs, defStyleRes)

    private fun canPlaceOnTheSameLine(filterItem: View, index: Int): Boolean {
        if (mPrevItem != null) {
            val prevItemWidth = mPrevItem?.measuredWidth ?: 0
            val itemWidth = filterItem.measuredWidth
            val occupiedWidth = mPrevX + prevItemWidth + margin + itemWidth
//            Log.d(appTag, "occupiedWidth[$occupiedWidth]measuredWidth[$measuredWidth]index[$index]")

            return occupiedWidth <= measuredWidth
        }

        return false
    }

    private fun calculateDesiredHeight(): Int {
        var height: Int = mPrevHeight

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

            when {
                mPrevItem == null -> {
                    mPrevX = margin
                    mPrevY = margin
                    height = child.measuredHeight + margin
                }
                canPlaceOnTheSameLine(child, i) -> {
                    mPrevX += (mPrevItem?.measuredWidth ?: 0) + margin / 2
                }
                else -> {
                    mPrevX = margin
                    mPrevY += (mPrevItem?.measuredHeight ?: 0) + margin / 2
                    height += child.measuredHeight + margin / 2
                }
            }
            mSize?.add(Coordinate(mPrevX, mPrevY))
            mPrevItem = child
        }

        height = if (height > 0) height + margin else 0
        mPrevHeight = height

        return mPrevHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Log.d(appTag, "onMeasure() widthMeasureSpec[${MeasureSpec.toString(widthMeasureSpec)}]")
//        Log.d(appTag, "onMeasure() heightMeasureSpec[${MeasureSpec.toString(heightMeasureSpec)}]")
        setMeasuredDimension(calculateSize(widthMeasureSpec, LayoutParams.MATCH_PARENT),
            calculateSize(heightMeasureSpec, calculateDesiredHeight()))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(appTag, "onDraw()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        Log.d(appTag, "onLayout()left[$left]top[$top]right[$right]bottom[$bottom][$childCount]")
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            mSize?.run {
                val coordinate: Coordinate = this[i]
                child.layout(coordinate.x,
                             coordinate.y,
                             coordinate.x + child.measuredWidth,
                             coordinate.y + child.measuredHeight)
            }
        }
    }

    fun setMargin(value: Int) {
        margin = value
    }

    fun setData(data: List<String>) {
        Log.d(appTag, "setData()")
        mData?.run {
            addAll(data)
        }
    }

    fun setListener(listener: FilterItemListener) {
        mListener = listener
    }

    fun build() {
        Log.d(appTag, "build()")
        mData?.forEachIndexed { i, data ->
            val view = FilterItemView(context)
            view.setData(data)
            view.setOnClickListener { itemView ->
                mListener?.run { onClickItem(itemView, i, data) }
            }
            addView(view)
        }
    }

    fun clear() {
        this.removeAllViews()
        mData?.clear()
        mSize?.clear()
        mListener = null
    }

    override fun onAttachedToWindow() {
        Log.d(appTag, "onAttachedToWindow()")
        super.onAttachedToWindow()
        mData = ArrayList()
        mSize = ArrayList()
    }

    override fun onDetachedFromWindow() {
        Log.d(appTag, "onDetachedFromWindow()")
        super.onDetachedFromWindow()
        mData = null
        mSize = null
        mListener = null
    }

    inner class Coordinate(var x: Int, var y: Int): Serializable
}