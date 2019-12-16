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
    private var mSize: LinkedHashMap<Int, Coordinate>? = null
    private var mPrevX: Int = 0
    private var mPrevY: Int = 0
    private var mPrevHeight = 0
    private var marginEnd = 8.toDp
    private var marginBottom = 12.toDp
    private var tune = 1
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
            val occupiedWidth = mPrevX + prevItemWidth + marginEnd + itemWidth
//            Log.d(appTag, "occupiedWidth[$occupiedWidth]measuredWidth[$measuredWidth]index[$index]")

            return occupiedWidth <= measuredWidth
        }

        return false
    }

    private fun calculateDesiredHeight(widthMeasureSpec: Int, heightMeasureSpec: Int): Int {
        var height: Int = mPrevHeight
        mPrevItem = null

        for (i in 0 until childCount) {
            val child: FilterItemView = getChildAt(i) as FilterItemView
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
//            Log.d(appTag, "child size i[$i], measuredWidth[${child.measuredWidth}], measuredHeight[${child.measuredHeight}]")
            when {
                mPrevItem == null -> {
                    mPrevX = 0
                    mPrevY = 0
                    height = child.measuredHeight + marginBottom
                }
                canPlaceOnTheSameLine(child, i) -> {
                    mPrevX += (mPrevItem?.measuredWidth ?: 0) + marginEnd / tune
                }
                else -> {
                    mPrevX = 0
                    mPrevY += (mPrevItem?.measuredHeight ?: 0) + marginBottom / tune
                    height += child.measuredHeight + marginBottom / tune
                }
            }
//            Log.d(appTag, "calculateDesiredHeight() i[$i], mPrevX[$mPrevX], mPrevY[$mPrevY]")
            mSize?.put(i, Coordinate(mPrevX, mPrevY))
            mPrevItem = child
        }
        height = if (height > 0) height else 0
        mPrevHeight = height

//        Log.d(appTag, "calculateDesiredHeight() mPrevHeight[$mPrevHeight]")
        return mPrevHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        Log.d(appTag, "onMeasure() widthMeasureSpec[${MeasureSpec.toString(widthMeasureSpec)}]")
//        Log.d(appTag, "onMeasure() heightMeasureSpec[${MeasureSpec.toString(heightMeasureSpec)}]")

        setMeasuredDimension(calculateSize(widthMeasureSpec, 0),
            calculateSize(heightMeasureSpec, calculateDesiredHeight(widthMeasureSpec, heightMeasureSpec)))
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
                val coordinate: Coordinate? = this[i]
                coordinate?.run {
//                    Log.d(appTag, "onLayout() i[$i], x[$x], y[$y], measuredWidth[${child.measuredWidth}], measuredHeight[${child.measuredHeight}]")
                    child.layout(x,
                        y,
                        x + child.measuredWidth,
                        y + child.measuredHeight)
                }
            }
        }
    }

    fun setEndMargin(value: Int) {
        marginEnd = value
    }

    fun setBottomMargin(value: Int) {
        marginBottom = value
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
            view.setOnClickListener {
                mListener?.run { onClickItem(view, i, data) }
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
        mSize = LinkedHashMap()
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