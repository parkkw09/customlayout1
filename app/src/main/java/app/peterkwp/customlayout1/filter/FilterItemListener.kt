package app.peterkwp.customlayout1.filter

import android.view.View

interface FilterItemListener {
    fun onClickItem(view: View, index: Int, data: String)
}