package app.peterkwp.customlayout1.ui.lists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import app.peterkwp.customlayout1.ui.lists.adapter.NestedAdapter
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PagingListNestedFragment: DaggerFragment() {

    companion object {
        fun newInstance() = PagingListNestedFragment()
    }

    @Inject
    lateinit var viewModelFactory: PagingListViewModelFactory
    private lateinit var viewModel: PagingListViewModel

    private val disposable: CompositeDisposable = CompositeDisposable()

    lateinit var recyclerView: RecyclerView
    lateinit var scroll: NestedScrollView
    lateinit var subTitle: TextView

    var adapter: NestedAdapter? = null

    var pageCount = 1
    var loading = false
    var complete = false

    private fun startTransaction(isRefresh: Boolean = false) {
        var totalCount = 0
        if (isRefresh) {
            pageCount = 1
            loading = false
            complete = false
        }
        viewModel.searchWeb("아이유", pageCount.toString())
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { result ->
                totalCount = result.meta?.totalCount ?: 0
                val value = "itemCount = $totalCount"
                subTitle.text = value
                result.documents?.let { list ->
                    if (pageCount == 1) adapter?.clearData()
                    if (list.isNotEmpty()) {
                        adapter?.addAllData(list)
                        if ((adapter?.size() ?: 0) >= totalCount
                            && pageCount != 1) {
                            App.COMPLETE
                        } else {
                            pageCount++
                            App.CONTINUE
                        }
                    } else {
                        App.END
                    }
                }
            }
            .subscribe({ status ->
                when (status) {
                    App.CONTINUE -> {
                        Log.d(App.TAG, "startTransaction() subscribe complete CONTINUE")
                        loading = false
                    }
                    App.COMPLETE -> {
                        Log.d(App.TAG, "startTransaction() subscribe complete COMPLETE")
                        adapter?.run {
                            noMoreFooter = true
                        }
                        loading = false
                        complete = true
                    }
                    else -> {
                        Log.d(App.TAG, "startTransaction() subscribe complete. END")
                    }
                }
            },{ throwable ->
                Log.d(App.TAG, "startTransaction() error[${throwable.message}]")
            }).apply {
                disposable.add(this)
            }
    }

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
        recyclerView = root.findViewById(R.id.list_result)
        scroll = root.findViewById(R.id.scroll)
        subTitle = root.findViewById(R.id.text_sub_title)

        return root
    }


    override fun onDestroyView() {
        Log.d(App.TAG, "onDestroyView()")
        super.onDestroyView()
        disposable.clear()
        adapter = null
        loading = false
        complete = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = NestedAdapter { model ->
            Log.d(App.TAG, "ItemClick [${model.title}]")
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PagingListNestedFragment.adapter
        }
        scroll.apply {
            setOnScrollChangeListener { view, _, scrollY, _, _ ->
                val calLastPosition = recyclerView.measuredHeight - view.measuredHeight
                if (scrollY >= calLastPosition) {
                    if (!loading && !complete) {
                        loading = true
                        startTransaction()
                    }
                }
            }
        }
        startTransaction(true)
    }
}