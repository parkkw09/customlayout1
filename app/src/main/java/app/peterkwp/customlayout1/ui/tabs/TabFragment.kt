package app.peterkwp.customlayout1.ui.tabs

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
import app.peterkwp.customlayout1.ui.parts.DefaultAdapter2
import com.google.android.material.tabs.TabLayout

class TabFragment : Fragment() {

    private lateinit var tabViewModel: TabViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tabViewModel =
            ViewModelProviders.of(this).get(TabViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tab, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        val viewpager: ViewPager = root.findViewById(R.id.viewpager)
        val adapter = DefaultAdapter2()

        tabViewModel.text.observe(this, Observer {
            textView.text = it
        })

        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
        return root
    }
}