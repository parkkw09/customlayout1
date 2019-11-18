package app.peterkwp.customlayout1.ui.kakaopay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.peterkwp.customlayout1.R

class KakaoPayFragment : Fragment() {

    private lateinit var kakaoPayViewModel: KakaoPayViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kakaoPayViewModel =
            ViewModelProviders.of(this).get(KakaoPayViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_kakaopay, container, false)
        val textView: TextView = root.findViewById(R.id.text_title)
        kakaoPayViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}