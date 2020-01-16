package app.peterkwp.customlayout1.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.ui.lists.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PagingListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: PagingListViewModelFactory
    private lateinit var viewModel: PagingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.run {
            viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(PagingListViewModel::class.java)
        }
        val root = inflater.inflate(R.layout.fragment_paging_tabs, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        val viewpager: ViewPager = root.findViewById(R.id.viewpager)

        viewModel.text.observe(this, Observer {
            textView.text = it
        })

        fragmentManager?.run {
            val adapter =
                FragmentAdapter(
                    this
                )
            viewpager.adapter = adapter
            tabs.setupWithViewPager(viewpager)
        }
        return root
    }
}