package app.peterkwp.customlayout1.pageindicator

import androidx.viewpager.widget.ViewPager

interface PageIndicator: ViewPager.OnPageChangeListener {

    fun setViewPager(view: ViewPager)
    fun setViewPager(view: ViewPager, initialPosition: Int)
    fun setCurrentItem(item: Int)
    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener)
    fun notifyDataSetChanged()
}