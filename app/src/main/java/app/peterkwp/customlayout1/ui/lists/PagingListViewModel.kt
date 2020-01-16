package app.peterkwp.customlayout1.ui.lists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.api.KakaoApi
import app.peterkwp.customlayout1.api.Model
import app.peterkwp.customlayout1.api.ModelDocuments
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class PagingListViewModel(private val api: KakaoApi): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Paging List Fragment"
    }
    val text: LiveData<String> = _text

    private var _response: MutableLiveData<List<ModelDocuments>>? = null
    val response: LiveData<List<ModelDocuments>>? get() = _response

    private var _error: MutableLiveData<String>? = null
    val error: LiveData<String>? get() = _error

    val isLoading: BehaviorSubject<Boolean>
            = BehaviorSubject.createDefault(false)

    fun onCreate() {
        _response = MutableLiveData()
    }

//    val defaultList: ArrayList<ModelDocuments> = ArrayList()
//    val footerList: ArrayList<ModelDocuments> = ArrayList()
//    val nestedList: ArrayList<ModelDocuments> = ArrayList()

    fun searchImage(query: String, page: String = "1"): Observable<Model>
        = api.searchImage(query, page)

    fun getImage(query: String, page: String = "1"): Disposable {
        isLoading.onNext(true)
        return api.searchImage(query, page = page)
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { isLoading.onNext(false) }
            .subscribe({ result ->
                if (result.documents!!.isEmpty()) {
                    if (page == "1") _error?.value = "검색 결과가 없습니다."
                } else {
                    _response?.value = result.documents
                }
            }, { throwable ->
                Log.e(App.TAG, throwable.message ?: "error")
                _error?.value = throwable.message
            })
    }

    fun searchWeb(query: String, page: String = "1"): Disposable {
        return api.searchWeb(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(App.TAG, "subscribe()")
                response.run {
                    documents?.run {
                        this.forEach { data ->
                            Log.d(App.TAG, "data()[${data.title}]")
                        }
                    }
                }
                _text.value = "search complete"
            },{ e ->
                Log.d(App.TAG, "exception()[${e.message}]")
            })
    }

    override fun onCleared() {
        Log.d(App.TAG, "onCleared()")
        _response = null
        super.onCleared()
    }
}