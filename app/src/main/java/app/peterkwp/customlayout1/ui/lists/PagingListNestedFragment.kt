package app.peterkwp.customlayout1.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.peterkwp.customlayout1.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PagingListNestedFragment: DaggerFragment() {

    companion object {
        fun newInstance() = PagingListNestedFragment()
    }

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
        val root = inflater.inflate(R.layout.fragment_paging_tabs_nested, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)

        viewModel.text.observe(this, Observer {
            val title = it + javaClass.simpleName
            textView.text = title
        })

        return root
    }
}