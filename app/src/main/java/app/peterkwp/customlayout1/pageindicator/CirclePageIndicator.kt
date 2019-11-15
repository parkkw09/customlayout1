package app.peterkwp.customlayout1.pageindicator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import app.peterkwp.customlayout1.R
import kotlinx.android.parcel.Parcelize
import kotlin.math.abs
import kotlin.math.min

class CirclePageIndicator
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.circlePageIndicatorStyle
) : View(context, attrs, defStyle), PageIndicator {

    companion object {
        private const val INVALID_POINTER = -1
    }

    private var mRadius: Float = 0.toFloat()
    private val mPaintPageFill = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintStroke = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintFill = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mViewPager: ViewPager? = null
    private var mListener: ViewPager.OnPageChangeListener? = null
    private var mCurrentPage: Int = 0
    private var mSnapPage: Int = 0
    private var mPageOffset: Float = 0.toFloat()
    private var mScrollState: Int = 0
    private var mOrientation: Int = 0
    private var mCentered: Boolean = false
    private var mSnap: Boolean = false

    private val mTouchSlop: Int
    private var mLastMotionX = -1f
    private var mActivePointerId = INVALID_POINTER
    private var mIsDragging: Boolean = false

    var isCentered: Boolean
        get() = mCentered
        set(centered) {
            mCentered = centered
            invalidate()
        }

    var pageColor: Int
        get() = mPaintPageFill.color
        set(pageColor) {
            mPaintPageFill.color = pageColor
            invalidate()
        }

    var fillColor: Int
        get() = mPaintFill.color
        set(fillColor) {
            mPaintFill.color = fillColor
            invalidate()
        }

    var orientation: Int
        get() = mOrientation
        set(orientation) = when (orientation) {
            LinearLayout.HORIZONTAL, LinearLayout.VERTICAL -> {
                mOrientation = orientation
                requestLayout()
            }

            else -> throw IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.")
        }

    var strokeColor: Int
        get() = mPaintStroke.color
        set(strokeColor) {
            mPaintStroke.color = strokeColor
            invalidate()
        }

    var strokeWidth: Float
        get() = mPaintStroke.strokeWidth
        set(strokeWidth) {
            mPaintStroke.strokeWidth = strokeWidth
            invalidate()
        }

    var radius: Float
        get() = mRadius
        set(radius) {
            mRadius = radius
            invalidate()
        }

    var isSnap: Boolean
        get() = mSnap
        set(snap) {
            mSnap = snap
            invalidate()
        }

    init {
        // Load defaults from resources
        val res = resources
        val defaultPageColor = res.getColor(R.color.default_circle_indicator_page_color, null)
        val defaultFillColor = res.getColor(R.color.default_circle_indicator_fill_color, null)
        val defaultOrientation = res.getInteger(R.integer.default_circle_indicator_orientation)
        val defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color, null)
        val defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width)
        val defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius)
        val defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered)
        val defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap)

        // Retrieve styles attributes
        val a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator, defStyle, 0)

        mCentered = a.getBoolean(R.styleable.CirclePageIndicator_centered, defaultCentered)
        mOrientation = a.getInt(R.styleable.CirclePageIndicator_android_orientation, defaultOrientation)
        mPaintPageFill.style = Paint.Style.FILL
        mPaintPageFill.color = a.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor)
        mPaintStroke.style = Paint.Style.STROKE
        mPaintStroke.color = a.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor)
        mPaintStroke.strokeWidth = a.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth)
        mPaintFill.style = Paint.Style.FILL
        mPaintFill.color = a.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor)
        mRadius = a.getDimension(R.styleable.CirclePageIndicator_radius, defaultRadius)
        mSnap = a.getBoolean(R.styleable.CirclePageIndicator_snap, defaultSnap)

        a.getDrawable(R.styleable.CirclePageIndicator_android_background)?.run {
            background = this
        }

        a.recycle()

        mTouchSlop = ViewConfiguration.get(context).scaledDoubleTapSlop
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mViewPager ?: return

        var count = 0
        mViewPager?.adapter?.let { adapter ->
            count = adapter.count
        }

        if (count == 0) return

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1)
            return
        }

        val longSize: Int
        val longPaddingBefore: Int
        val longPaddingAfter: Int
        val shortPaddingBefore: Int
        if (mOrientation == LinearLayout.HORIZONTAL) {
            longSize = width
            longPaddingBefore = paddingLeft
            longPaddingAfter = paddingRight
            shortPaddingBefore = paddingTop
        } else {
            longSize = height
            longPaddingBefore = paddingTop
            longPaddingAfter = paddingBottom
            shortPaddingBefore = paddingLeft
        }

        val threeRadius = mRadius * 3
        val shortOffset = shortPaddingBefore + mRadius
        var longOffset = longPaddingBefore + mRadius
        if (mCentered) {
            longOffset += (longSize - longPaddingBefore - longPaddingAfter) / 2.0f - count * threeRadius / 2.0f
        }

        var dX: Float
        var dY: Float

        var pageFillRadius = mRadius
        if (mPaintStroke.strokeWidth > 0) {
            pageFillRadius -= mPaintStroke.strokeWidth / 2.0f
        }

        // Draw stroked circles
        for (iLoop in 0 until count) {
            val drawLong = longOffset + iLoop * threeRadius
            if (mOrientation == LinearLayout.HORIZONTAL) {
                dX = drawLong
                dY = shortOffset
            } else {
                dX = shortOffset
                dY = drawLong
            }
            // Only paint fill if not completely transparent
            if (mPaintPageFill.alpha > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill)
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, mRadius, mPaintStroke)
            }
        }

        //Draw the filled circle according to the current scroll
        var cx = (if(mSnap) mSnapPage else mCurrentPage) * threeRadius
        if (!mSnap) {
            cx += mPageOffset * threeRadius
        }
        if (mOrientation == LinearLayout.HORIZONTAL) {
            dX = longOffset + cx
            dY = shortOffset
        } else {
            dX = shortOffset
            dY = longOffset + cx
        }
        canvas.drawCircle(dX, dY, mRadius, mPaintFill)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (super.onTouchEvent(ev)) {
            return true
        }

        val viewPager = mViewPager
        viewPager ?: return false
        val count = viewPager.adapter?.count ?: 0
        if (count == 0) return false

//        mViewPager?.run {
//            this.adapter?.apply {
//                if (count == 0) return false
//            } ?: return false
//        } ?: return false

//        if (mViewPager == null || mViewPager!!.adapter?.count == 0) {
//            return false
//        }

        when (val action = ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mActivePointerId = ev.getPointerId(0)
                mLastMotionX = ev.x
            }

            MotionEvent.ACTION_MOVE -> {
                val activePointerIndex = ev.findPointerIndex(mActivePointerId)
                val x = ev.getX(activePointerIndex)
                val deltaX = x - mLastMotionX

                if (!mIsDragging) {
                    if (abs(deltaX) > mTouchSlop) {
                        mIsDragging = true
                    }
                }

                if (mIsDragging) {
                    mLastMotionX = x
                    if (viewPager.isFakeDragging || viewPager.beginFakeDrag()) {
                        viewPager.fakeDragBy(deltaX)
                    }
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (!mIsDragging) {
//                    val count = viewPager.adapter?.count
                    val width = width
                    val halfWidth = width / 2f
                    val sixthWidth = width / 6f

                    if (mCurrentPage > 0 && ev.x < halfWidth - sixthWidth) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            viewPager.currentItem = mCurrentPage - 1
                        }
                        return true
                    } else if (mCurrentPage < count - 1 && ev.x > halfWidth + sixthWidth) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            viewPager.currentItem = mCurrentPage + 1
                        }
                        return true
                    }
                }

                mIsDragging = false
                mActivePointerId = INVALID_POINTER
                if (viewPager.isFakeDragging) viewPager.endFakeDrag()
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = ev.actionIndex
                mLastMotionX = ev.getX(index)
                mActivePointerId = ev.getPointerId(index)
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = ev.actionIndex
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                }
                mLastMotionX = ev.getX(ev.findPointerIndex(mActivePointerId))
            }
        }

        return true
    }

    override fun setViewPager(view: ViewPager) {
        val viewPager = mViewPager
        viewPager ?: return
        if (viewPager === view) return

        viewPager.apply {
            removeOnPageChangeListener(this@CirclePageIndicator)
            checkNotNull(view.adapter) { "ViewPager does not have adapter instance." }
        }

        mViewPager = view
        mViewPager?.apply {
            addOnPageChangeListener(this@CirclePageIndicator)
            invalidate()
        }
    }

    override fun setViewPager(view: ViewPager, initialPosition: Int) {
        setViewPager(view)
        setCurrentItem(initialPosition)
    }

    override fun setCurrentItem(item: Int) {
        val viewPager = mViewPager
        viewPager ?: run {
            throw IllegalStateException("ViewPager has not been bound.")
        }
        viewPager.currentItem = item
        mCurrentPage = item
        invalidate()
    }

    override fun notifyDataSetChanged() {
        invalidate()
    }

    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state
        mListener?.apply { onPageScrollStateChanged(state) }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mCurrentPage = position
        mPageOffset = positionOffset
        invalidate()
        mListener?.apply { onPageScrolled(position, positionOffset, positionOffsetPixels) }
    }

    override fun onPageSelected(position: Int) {
        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position
            mSnapPage = position
            invalidate()
        }
        mListener.apply { onPageSelected(position) }
    }

    override fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        mListener = listener
    }

    private fun measureLong(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY || mViewPager == null) {
            //We were told how big to be
            result = specSize
        } else {
            //Calculate the width according the views count
            val count = mViewPager?.adapter?.count ?: 0
            result = (paddingLeft.toFloat() + paddingRight.toFloat()
                    + count.toFloat() * 2f * mRadius + (count - 1) * mRadius + 1f).toInt()
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }

    private fun measureShort(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize
        } else {
            //Measure the height
            result = (2 * mRadius + paddingTop.toFloat() + paddingBottom.toFloat() + 1f).toInt()
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mOrientation == LinearLayout.HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec))
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec))
        }
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        mCurrentPage = savedState.currentPage
        mSnapPage = savedState.currentPage
        requestLayout()
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.currentPage = mCurrentPage
        return savedState
    }

    @SuppressLint("ParcelCreator")
    class SavedState : BaseSavedState {
        var currentPage: Int = 0

        constructor(superState: Parcelable?) : super(superState)
        private constructor(`in`: Parcel) : super(`in`) {
            currentPage = `in`.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(currentPage)
        }

        companion object {
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}