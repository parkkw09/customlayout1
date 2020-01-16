package app.peterkwp.customlayout1.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.autoviewpager.AutoScrollViewPager
import app.peterkwp.customlayout1.ui.parts.DefaultPageAdapter
import com.viewpagerindicator.CirclePageIndicator

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)
        val viewPager: AutoScrollViewPager = root.findViewById(R.id.viewpager)
        val indicator: CirclePageIndicator = root.findViewById(R.id.indicator)
        val adapter = DefaultPageAdapter()

        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        viewPager.adapter = adapter
        viewPager.setScrollFactor(5.0)
        viewPager.offscreenPageLimit = 3
        indicator.setViewPager(viewPager)
        indicator.isSnap = true
        viewPager.startAutoScroll(2000)
        viewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(App.TAG, "onPageSelected() getCurrentItem[${viewPager.currentItem}]")
            }
        })
        Log.d(App.TAG, "getCurrentItem[${viewPager.currentItem}]")

        return root
    }
}