package app.peterkwp.customlayout1.ui.lists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.api.KakaoApi
import app.peterkwp.customlayout1.api.Model
import io.reactivex.Observable

class PagingListViewModel(private val api: KakaoApi): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Paging List Fragment"
    }
    val text: LiveData<String> = _text

    fun searchImage(query: String, page: String = "1"): Observable<Model>
        = api.searchImage(query, page)

    fun searchWeb(query: String, page: String = "1"): Observable<Model>
            = api.searchWeb(query, page)

    override fun onCleared() {
        Log.d(App.TAG, "onCleared()")
        super.onCleared()
    }
}