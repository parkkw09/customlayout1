package app.peterkwp.customlayout1.ui.kakaopay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.peterkwp.customlayout1.api.KakaoApi

class KakaoPayViewModelFactory(val api: KakaoApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return KakaoPayViewModel(api) as T
    }
}