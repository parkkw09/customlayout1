package app.peterkwp.customlayout1.ui.filtersticker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.filter.FilterItemListener
import app.peterkwp.customlayout1.filter.FilterView
import app.peterkwp.customlayout1.toDp
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FilterStickerFragment : DaggerFragment(), FilterItemListener {

    @Inject
    lateinit var viewModelFactory: FilterStickerViewModelFactory
    private lateinit var filterStickerViewModel: FilterStickerViewModel

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun getData2(): Disposable {
        return filterStickerViewModel.searchRepo("ai")
    }

    private fun initUI(view: View) {
        val textView: TextView = view.findViewById(R.id.text_title)
        val filterView: FilterView = view.findViewById(R.id.filter)
        filterView.setListener(this)

        filterStickerViewModel.text.observe(this, Observer {
            textView.text = it
        })

        filterStickerViewModel.complete.observe(this, Observer {
            if (it) {
                filterView.setEndMargin(10.toDp)
                filterView.setBottomMargin(10.toDp)
                filterView.setData(filterStickerViewModel.repoNameList)
                filterView.build()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterStickerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FilterStickerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_filtersticker, container, false)
        initUI(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(App.TAG, "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        getData2().apply {
            disposable.add(this)
        }
    }

    override fun onDestroyView() {
        Log.d(App.TAG, "onDestroyView()")
        super.onDestroyView()
        filterStickerViewModel.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(App.TAG, "onCreate()")
    }

    override fun onDestroy() {
        Log.d(App.TAG, "onDestroy()")
        disposable.clear()
        super.onDestroy()
    }

    override fun onClickItem(view: View, index: Int, data: String) {
        Snackbar.make(view , "index[$index], data[$data]", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
    }
}