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
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FilterStickerFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: FilterStickerViewModelFactory
    private lateinit var filterStickerViewModel: FilterStickerViewModel

    private val disposable: CompositeDisposable = CompositeDisposable()

    private fun getData(): Disposable {
        return filterStickerViewModel.searchWeb("아이유")
    }

    private fun initUI(view: View) {
        val textView: TextView = view.findViewById(R.id.text_title)

        filterStickerViewModel.text.observe(this, Observer {
            textView.text = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterStickerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FilterStickerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_filterstcker, container, false)
        initUI(root)
        return root
    }

    override fun onResume() {
        Log.d(App.TAG, "onResume()")
        super.onResume()
        getData().apply {
            disposable.add(this)
        }
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
}