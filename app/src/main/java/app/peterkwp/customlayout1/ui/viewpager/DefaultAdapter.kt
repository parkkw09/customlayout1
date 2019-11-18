package app.peterkwp.customlayout1.ui.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import app.peterkwp.customlayout1.R

class DefaultAdapter: PagerAdapter() {

    private val list = arrayOf("page1", "page2", "page3", "page4", "page5")

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_viewpage, null)
        container.addView(view)

        val text: TextView = view.findViewById(R.id.text_name)
        text.text = list[position]
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount() = list.size
}