package app.peterkwp.customlayout1.ui.kakaopay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.api.InicisApi
import app.peterkwp.customlayout1.api.KakaoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URLDecoder
import java.net.URLEncoder

class KakaoPayViewModel(val api: KakaoApi, val api2: InicisApi) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kakao pay transaction test"
    }
    val text: LiveData<String> = _text
    val url: MutableLiveData<String> = MutableLiveData()
    val responseBody: MutableLiveData<String> = MutableLiveData()
    var tid: String? = null

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
                response.next_redirect_mobile_url?.run {
                    response.tid?.let { tid ->
                        this@KakaoPayViewModel.tid = tid
                    }
                    url.value = this
                }
            },{ e ->
                Log.d(App.TAG, "exception()[${e.message}]")
            })
    }

    /**
    cid	가맹점 코드. 10자.	O	String
    cid_secret	가맹점 코드 인증키. 24자 숫자+영문 소문자	X	String
    tid	결제 고유번호. 결제준비 API의 응답에서 얻을 수 있음	O	String
    partner_order_id	가맹점 주문번호. 결제준비 API에서 요청한 값과 일치해야 함	O	String
    partner_user_id	가맹점 회원 id. 결제준비 API에서 요청한 값과 일치해야 함	O	String
    pg_token	결제승인 요청을 인증하는 토큰. 사용자가 결제수단 선택 완료시 approval_url로 redirection해줄 때 pg_token을 query string으로 넘겨줌	O	String
    payload	해당 Request와 매핑해서 저장하고 싶은 값. 최대 200자	X	String
    total_amount	상품총액. 결제준비 API에서 요청한 total_amount 값과 일치해야 함	X	String
     */
    fun transactionApprove(
        cid: String,
        cid_secret: String? = null,
        tid: String,
        partner_order_id: String,
        partner_user_id: String,
        pg_token: String,
        payload: String? = null,
        total_amount: String? = null
    ): Disposable {
        return api.transactionApprove(
                cid = cid,
                cid_secret = cid_secret,
                tid = tid,
                partner_user_id = partner_user_id,
                partner_order_id = partner_order_id,
                pg_token = pg_token,
                payload = payload,
                total_amount = total_amount
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(App.TAG, "subscribe()")
                _text.value = "transaction complete approved time[${response.approved_at}]"
            },{ e ->
                Log.d(App.TAG, "exception()[${e.message}]")
            })
    }

    fun transactionReady2(payment: String?,
        mid: String?,
        oid: String? = null,
        amt: String?,
        uname: String?,
        mname: String? = null,
        noti: String? = null,
        goods: String?,
        mobile: String? = null,
        email: String? = null,
        next_url: String? = null,
        noti_url: String? = null,
        tax: String? = null,
        taxfree: String? = null,
        offerperiod: String? = null,
        cardoption: String? = null,
        cardcode: String? = null,
        quotabase: String? = null,
        hpp_method: String? = null,
        vbank_dt: String? = null,
        vbank_tm: String? = null,
        charset: String? = null,
        reserved: String? = null
    ): Disposable {
        return api2.transactionReady2(
            payment = payment,
            mid = mid,
            oid = oid,
            amt = amt,
            uname = URLEncoder.encode(uname, "euc-kr"),
            mname = URLEncoder.encode(mname, "euc-kr"),
            noti = noti,
            goods = URLEncoder.encode(goods, "euc-kr"),
            mobile = mobile,
            email = email,
            next_url = next_url,
            noti_url = noti_url,
            tax = tax,
            taxfree = taxfree,
            offerperiod = offerperiod,
            cardoption = cardoption,
            cardcode = cardcode,
            quotabase = quotabase,
            hpp_method = hpp_method,
            vbank_dt = vbank_dt,
            vbank_tm = vbank_tm,
            charset = charset,
            reserved = reserved)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(App.TAG, "subscribe()")
                val convertResponse = URLDecoder.decode(response, "euc-kr")
                // Log.d(App.TAG, "subscribe() [$convertResponse]")
                responseBody.value = convertResponse
            },{ e ->
                Log.d(App.TAG, "exception()[${e.message}]")
            })
    }
}