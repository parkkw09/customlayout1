package app.peterkwp.customlayout1.ui.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.peterkwp.customlayout1.api.KakaoApi

class PagingListViewModelFactory(val api: KakaoApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return PagingListViewModel(api) as T
    }
}