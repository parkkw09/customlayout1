package app.peterkwp.customlayout1.autoviewpager

import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class AutoScrollPagerAdapter(wrapped: PagerAdapter?) : PagerAdapter() {

    private val wrappedAdapter = wrapped

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return wrappedAdapter!!.isViewFromObject(view, `object`)
    }

    override fun getCount(): Int {
        return when {
            wrappedAdapter == null -> {
                0
            }
            wrappedAdapter.count > 1 -> {
                wrappedAdapter.count + 2
            }
            else -> {
                wrappedAdapter.count
            }
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return when (position) {
            0 -> {
                wrappedAdapter!!.instantiateItem(container, wrappedAdapter.count - 1)
            }
            wrappedAdapter!!.count + 1 -> {
                wrappedAdapter.instantiateItem(container, 0)
            }
            else -> {
                wrappedAdapter.instantiateItem(container, position - 1)
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        wrappedAdapter!!.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }

    override fun finishUpdate(container: ViewGroup) {
        super.finishUpdate(container)
        wrappedAdapter?.finishUpdate(container)
    }

    override fun restoreState(bundle: Parcelable?, classLoader: ClassLoader?) {
        wrappedAdapter?.restoreState(bundle, classLoader)
        super.restoreState(bundle, classLoader)
    }

    override fun saveState(): Parcelable? {
        return if (wrappedAdapter != null) {
            wrappedAdapter.saveState()
        } else super.saveState()
    }

    override fun startUpdate(container: ViewGroup) {
        super.startUpdate(container)
        wrappedAdapter?.startUpdate(container)
    }
}