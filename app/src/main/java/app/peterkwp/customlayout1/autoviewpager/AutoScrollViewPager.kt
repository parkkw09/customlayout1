package app.peterkwp.customlayout1.autoviewpager

import android.content.Context
import android.database.DataSetObserver
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.animation.Interpolator
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field
import java.util.*
import kotlin.math.abs

class AutoScrollViewPager : ViewPager {
    private val innerOnPageChangeListener = InnerOnPageChangeListener()

    interface OnPageClickListener {
        fun onPageClick(pager: AutoScrollViewPager?, position: Int)
    }

    private var wrappedPagerAdapter: PagerAdapter? = null
    private var wrapperPagerAdapter: PagerAdapter? = null
    private var mOnPageChangeListener: OnPageChangeListener? = null
    private val mOnPageChangeListeners: MutableList<OnPageChangeListener> = LinkedList()
    private var scroller: AutoScrollFactorScroller? = null
    private var handler: H? = null
    private val mObserver: InnerDataSetObserver? = InnerDataSetObserver()
    private var autoScroll = false
    private var intervalInMillis = 0
    private var mInitialMotionX = 0f
    private var mInitialMotionY = 0f
    private var touchSlop = 0
    var onPageClickListener: OnPageClickListener? = null

    private inner class H : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_AUTO_SCROLL -> {
                    currentItem += 1
                    sendEmptyMessageDelayed(
                        MSG_AUTO_SCROLL,
                        intervalInMillis.toLong()
                    )
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    private fun init() {
        super.addOnPageChangeListener(innerOnPageChangeListener)
        handler = H()
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pauseAutoScroll()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (autoScroll) {
            startAutoScroll()
        }
    }

    fun startAutoScroll() {
        startAutoScroll(if (intervalInMillis != 0) intervalInMillis else DEFAULT_INTERNAL_IM_MILLIS)
    }

    fun startAutoScroll(intervalInMillis: Int) { // Only post scroll message when necessary.
        if (getCount() > 1) {
            this.intervalInMillis = intervalInMillis
            autoScroll = true
            pauseAutoScroll()
            handler!!.sendEmptyMessageDelayed(
                MSG_AUTO_SCROLL,
                intervalInMillis.toLong()
            )
        }
    }

    fun stopAutoScroll() {
        autoScroll = false
        pauseAutoScroll()
    }

    fun setInterval(intervalInMillis: Int) {
        this.intervalInMillis = intervalInMillis
    }

    fun setScrollFactor(factor: Double) {
        setScrollerIfNeeded()
        scroller!!.factor = factor
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (autoScroll) {
            if (hasWindowFocus) {
                startAutoScroll()
            } else {
                pauseAutoScroll()
            }
        }
    }

    override fun setOnPageChangeListener(listener: OnPageChangeListener?) {
        mOnPageChangeListener = listener
    }

    override fun addOnPageChangeListener(listener: OnPageChangeListener) {
        mOnPageChangeListeners.add(listener)
    }

    override fun clearOnPageChangeListeners() {
        super.clearOnPageChangeListeners()
        mOnPageChangeListeners.clear()
        super.addOnPageChangeListener(innerOnPageChangeListener)
    }

    override fun getAdapter(): PagerAdapter? {
        // In order to be compatible with ViewPagerIndicator
        return wrappedPagerAdapter
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        if (wrappedPagerAdapter != null && mObserver != null) {
            wrappedPagerAdapter!!.unregisterDataSetObserver(mObserver)
        }
        wrappedPagerAdapter = adapter
        if (wrappedPagerAdapter != null && mObserver != null) {
            wrappedPagerAdapter!!.registerDataSetObserver(mObserver)
        }
        wrapperPagerAdapter =
            if (wrappedPagerAdapter == null) null else AutoScrollPagerAdapter(
                adapter
            )
        super.setAdapter(wrapperPagerAdapter)

        setCurrentItem(0, false)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item + 1)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item + 1, smoothScroll)
    }

    override fun getCurrentItem(): Int {
        var curr = super.getCurrentItem()
        if (wrappedPagerAdapter != null && wrappedPagerAdapter!!.count > 1) {
            curr = when (curr) {
                0 -> {
                    wrappedPagerAdapter!!.count - 1
                }
                wrapperPagerAdapter!!.count - 1 -> {
                    0
                }
                else -> {
                    curr - 1
                }
            }
        }
        return curr
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (getCurrentItemOfWrapper() + 1 == getCountOfWrapper()) {
                    setCurrentItem(0, false)
                } else if (getCurrentItemOfWrapper() == 0) {
                    setCurrentItem(getCount() - 1, false)
                }
                pauseAutoScroll()
                mInitialMotionX = ev.x
                mInitialMotionY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val lastMotionX = ev.x
                val lastMotionY = ev.y
                if (abs(lastMotionX - mInitialMotionX).toInt() > touchSlop
                    || abs(lastMotionY - mInitialMotionY).toInt() > touchSlop
                ) {
                    mInitialMotionX = 0.0f
                    mInitialMotionY = 0.0f
                }
            }
            MotionEvent.ACTION_UP -> {
                if (autoScroll) {
                    startAutoScroll()
                }
                // Manually swipe not affected by scroll factor.
                if (scroller != null) {
                    val lastFactor = scroller!!.factor
                    scroller!!.factor = 1.0
                    post{
                        scroller!!.factor = lastFactor
                    }
                }
                val lastMotionX = ev.x
                val lastMotionY = ev.y
                if (mInitialMotionX.toInt() != 0 && mInitialMotionY.toInt() != 0) {
                    if (abs(lastMotionX - mInitialMotionX).toInt() < touchSlop
                        && abs(lastMotionY - mInitialMotionY).toInt() < touchSlop
                    ) {
                        mInitialMotionX = 0.0f
                        mInitialMotionY = 0.0f
                        if (onPageClickListener != null) {
                            onPageClickListener!!.onPageClick(this, currentItem)
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    /**
     * Get current item of the outer wrapper adapter.
     */
    private fun getCurrentItemOfWrapper(): Int {
        return super.getCurrentItem()
    }

    /**
     * Get item count of the outer wrapper adapter.
     */
    private fun getCountOfWrapper(): Int {
        return if (wrapperPagerAdapter != null) {
            wrapperPagerAdapter!!.count
        } else 0
    }

    /**
     * Get item count of the adapter which is set by user
     */
    private fun getCount(): Int {
        return if (wrappedPagerAdapter != null) {
            wrappedPagerAdapter!!.count
        } else 0
    }

    private fun setScrollerIfNeeded() {
        if (scroller != null) {
            return
        }
        try {
            val scrollerField: Field =
                ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            val interpolatorField: Field =
                ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolatorField.isAccessible = true
            scroller = AutoScrollFactorScroller(
                context,
                interpolatorField[null] as Interpolator
            )
            scrollerField[this] = scroller
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pauseAutoScroll() {
        handler!!.removeMessages(MSG_AUTO_SCROLL)
    }

    inner class InnerOnPageChangeListener : OnPageChangeListener {
        private var mLastSelectedPage = -1

        override fun onPageScrollStateChanged(state: Int) {
            if (state == SCROLL_STATE_IDLE && getCount() > 1) {
                if (getCurrentItemOfWrapper() == 0) { // scroll to the last page
                    setCurrentItem(getCount() - 1, false)
                } else if (getCurrentItemOfWrapper() == getCountOfWrapper() - 1) { // scroll to the first page
                    setCurrentItem(0, false)
                }
            }
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener!!.onPageScrollStateChanged(state)
            }
            for (onPageChangeListener in mOnPageChangeListeners) {
                onPageChangeListener.onPageScrollStateChanged(state)
            }
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (mOnPageChangeListener != null && position > 0 && position < getCount()) {
                mOnPageChangeListener!!.onPageScrolled(
                    position - 1,
                    positionOffset,
                    positionOffsetPixels
                )
            }
            for (onPageChangeListener in mOnPageChangeListeners) {
                if (position > 0 && position < getCount()) {
                    onPageChangeListener.onPageScrolled(
                        position - 1,
                        positionOffset,
                        positionOffsetPixels
                    )
                }
            }
        }

        override fun onPageSelected(position: Int) {
            if (mOnPageChangeListener != null || mOnPageChangeListeners.size > 0) {
                val pos: Int
                // Fix position
                pos = when (position) {
                    0 -> {
                        getCount() - 1
                    }
                    getCountOfWrapper() - 1 -> {
                        0
                    }
                    else -> {
                        position - 1
                    }
                }
                if (mLastSelectedPage != pos) {
                    mLastSelectedPage = pos
                    if (mOnPageChangeListener != null) {
                        mOnPageChangeListener!!.onPageSelected(pos)
                    }
                    for (onPageChangeListener in mOnPageChangeListeners) {
                        onPageChangeListener.onPageSelected(pos)
                    }
                }
            }
        }
    }

    inner class InnerDataSetObserver : DataSetObserver() {
        override fun onChanged() {
            if (wrapperPagerAdapter != null) {
                wrapperPagerAdapter!!.notifyDataSetChanged()
            }
        }

        override fun onInvalidated() {
            if (wrapperPagerAdapter != null) {
                wrapperPagerAdapter!!.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val MSG_AUTO_SCROLL = 0
        private const val DEFAULT_INTERNAL_IM_MILLIS = 2000
    }
}