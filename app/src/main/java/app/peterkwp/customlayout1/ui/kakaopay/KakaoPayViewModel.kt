package app.peterkwp.customlayout1.ui.kakaopay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.api.KakaoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class KakaoPayViewModel(val api: KakaoApi) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kakao pay transaction test"
    }
    val text: LiveData<String> = _text
    val url: MutableLiveData<String> = MutableLiveData()

    fun transactionReady(
        cid: String?,
        cid_secret: String? = null,
        partner_order_id: String?,
        partner_user_id: String?,
        item_name: String?,
        item_code: String? = null,
        quantity: String?,
        total_amount: String?,
        tax_free_amount: String?,
        vat_amount: String? = null,
        approval_url: String?,
        cancel_url: String?,
        fail_url: String?,
        available_cards: String? = null,
        payment_method_type: String? = null,
        install_month: String? = null,
        custom_json: String? = null
    ): Disposable {
        return api.transactionReady(
            cid = cid,
            cid_secret = cid_secret,
            partner_order_id = partner_order_id,
            partner_user_id = partner_user_id,
            item_name = item_name,
            item_code = item_code,
            quantity =  quantity,
            total_amount = total_amount,
            tax_free_amount = tax_free_amount,
            vat_amount = vat_amount,
            approval_url = approval_url,
            cancel_url = cancel_url,
            fail_url = fail_url,
            available_cards = available_cards,
            payment_method_type = payment_method_type,
            install_month = install_month,
            custom_json = custom_json)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(App.TAG, "subscribe()")
                response.next_redirect_mobile_url?.apply {
                    url.value = this
                }
            },{ e ->
                Log.d(App.TAG, "exception()[${e.message}]")
            })
    }

    fun transactionTest(): Disposable {
        return transactionReady(
            cid = "TC0ONETIME",
            partner_order_id = "partner_order_id",
            partner_user_id = "partner_user_id",
            item_name = "초코파이",
            quantity =  "1",
            total_amount = "2200",
            tax_free_amount = "200",
            vat_amount = "0",
            approval_url = "https://developers.kakao.com/success",
            cancel_url = "https://developers.kakao.com/fail",
            fail_url = "https://developers.kakao.com/cancel"
        )
    }
}