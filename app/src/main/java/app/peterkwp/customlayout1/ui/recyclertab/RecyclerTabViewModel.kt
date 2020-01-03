package app.peterkwp.customlayout1.ui.recyclertab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecyclerTabViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is RecyclerTab Fragment"
    }
    val text: LiveData<String> = _text
}