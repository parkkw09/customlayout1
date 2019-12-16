package app.peterkwp.customlayout1

import android.content.res.Resources
import android.view.View

val Int.toPx: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal fun calculateSize(measureSpec: Int, desiredSize: Int): Int {
    val mode = View.MeasureSpec.getMode(measureSpec)
    val size = View.MeasureSpec.getSize(measureSpec)

    return when (mode) {
        View.MeasureSpec.EXACTLY, View.MeasureSpec.AT_MOST -> size
        else -> desiredSize
    }
}