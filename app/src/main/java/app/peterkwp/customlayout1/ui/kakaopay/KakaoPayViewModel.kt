package app.peterkwp.customlayout1.ui.kakaopay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KakaoPayViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kakao pay transaction test"
    }
    val text: LiveData<String> = _text
}