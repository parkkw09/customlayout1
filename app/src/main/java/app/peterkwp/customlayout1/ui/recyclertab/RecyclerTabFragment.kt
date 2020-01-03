package app.peterkwp.customlayout1.ui.recyclertab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.peterkwp.customlayout1.R

class RecyclerTabFragment : Fragment() {

    private lateinit var recyclerTabViewModel: RecyclerTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerTabViewModel =
            ViewModelProviders.of(this).get(RecyclerTabViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tab, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)
        recyclerTabViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}