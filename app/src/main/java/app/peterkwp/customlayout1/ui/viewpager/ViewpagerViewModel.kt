package app.peterkwp.customlayout1.ui.viewpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewpagerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Viewpager indicator test"
    }
    val text: LiveData<String> = _text
}