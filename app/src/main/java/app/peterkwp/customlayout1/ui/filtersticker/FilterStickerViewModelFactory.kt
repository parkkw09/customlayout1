package app.peterkwp.customlayout1.ui.filtersticker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.peterkwp.customlayout1.api.GithubApi
import app.peterkwp.customlayout1.api.KakaoApi

class FilterStickerViewModelFactory(val api: KakaoApi, val api2: GithubApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FilterStickerViewModel(api, api2) as T
    }
}