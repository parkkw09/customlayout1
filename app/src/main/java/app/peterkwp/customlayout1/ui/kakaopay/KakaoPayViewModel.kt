package app.peterkwp.customlayout1.ui.kakaopay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peterkwp.customlayout1.api.KakaoApi

class KakaoPayViewModel(val api: KakaoApi) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kakao pay transaction test"
    }
    val text: LiveData<String> = _text
}