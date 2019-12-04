package app.peterkwp.customlayout1.ui.filtersticker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.api.GithubApi
import app.peterkwp.customlayout1.api.KakaoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FilterStickerViewModel(val api: KakaoApi, val api2: GithubApi) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Filter Sticker Fragment"
    }
    val text: LiveData<String> = _text
    val complete: MutableLiveData<Boolean> = MutableLiveData()
    val repoNameList: ArrayList<String> = ArrayList()

    fun searchWeb(query: String): Disposable {
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

    fun searchRepo(query: String): Disposable {
        return api2.searchRepository(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(App.TAG, "subscribe()")
                response.items?.forEach {
//                    Log.d(App.TAG, "name[${it.name}]")
                    it.name?.run {
                        repoNameList.add(this)
                    }
                }
                if (repoNameList.isNotEmpty()) {
                    complete.value = true
                }

                _text.value = "search complete total count[${repoNameList.size}]"
            },{ e ->
                Log.d(App.TAG, "exception()[${e.message}]")
            })
    }

    fun clear() {
        repoNameList.clear()
    }

    override fun onCleared() {
        super.onCleared()
        repoNameList.clear()
    }
}