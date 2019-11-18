package app.peterkwp.customlayout1.ui.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.pageindicator.CirclePageIndicator

class ViewpagerFragment : Fragment() {

    private lateinit var viewpagerViewModel: ViewpagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewpagerViewModel =
            ViewModelProviders.of(this).get(ViewpagerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_viewpager, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)
        val viewPager: ViewPager = root.findViewById(R.id.viewpager)
        val indicator: CirclePageIndicator = root.findViewById(R.id.indicator)
        val adapter = DefaultAdapter()

        viewpagerViewModel.text.observe(this, Observer {
            textView.text = it
        })

        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)

        return root
    }
}