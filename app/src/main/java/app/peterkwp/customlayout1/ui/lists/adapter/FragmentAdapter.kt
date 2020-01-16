package app.peterkwp.customlayout1.ui.lists.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.peterkwp.customlayout1.ui.lists.PagingListDefaultFragment
import app.peterkwp.customlayout1.ui.lists.PagingListFooterFragment
import app.peterkwp.customlayout1.ui.lists.PagingListNestedFragment

class FragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val list = arrayOf("Default", "Footer", "Nested")

    override fun getItem(position: Int): Fragment {
        return when (list[position]) {
            "Default" -> PagingListDefaultFragment.newInstance()
            "Footer" -> PagingListFooterFragment.newInstance()
            "Nested" -> PagingListNestedFragment.newInstance()
            else -> PagingListDefaultFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }

    override fun getCount() = list.size
}