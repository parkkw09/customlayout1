package app.peterkwp.customlayout1.ui.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TabViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Tabs Fragment"
    }
    val text: LiveData<String> = _text
}