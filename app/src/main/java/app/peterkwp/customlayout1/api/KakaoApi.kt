package app.peterkwp.customlayout1.api

import app.peterkwp.customlayout1.feature.AppConst
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface KakaoApi {

    @GET(AppConst.API_KAKAO_SEARCH_IMAGE)
    fun searchImage(@Query("query") query: String,
                    @Query("page") page: String = "1",
                    @Query("size") size: String = "10"): Observable<Model>

    /**
        cid	가맹점 코드. 10자.	O	String
        cid_secret	가맹점 코드 인증키. 24자 숫자+영문 소문자	X	String
        partner_order_id	가맹점 주문번호. 최대 100자	O	String
        partner_user_id	가맹점 회원 id. 최대 100자	O	String
        item_name	상품명. 최대 100자	O	String
        item_code	상품코드. 최대 100자	X	String
        quantity	상품 수량	O	Integer
        total_amount	상품 총액	O	Integer
        tax_free_amount	상품 비과세 금액	O	Integer
        vat_amount	상품 부가세 금액(안보낼 경우 (상품총액 - 상품 비과세 금액)/11 : 소숫점이하 반올림)	X	Integer
        approval_url	결제 성공시 redirect url. 최대 255자	O	String
        cancel_url	결제 취소시 redirect url. 최대 255자	O	String
        fail_url	결제 실패시 redirect url. 최대 255자	O	String
        available_cards	카드사 제한 목록(없을 경우 전체)
        현재 SHINHAN, KB, HYUNDAI, LOTTE, SAMSUNG, NH, BC, HANA, CITI, KAKAOBANK, KAKAOPAY, WOORI, GWANGJU, SUHYUP, SHINHYUP, JEONBUK, JEJU, SC 지원.
        ex) [“HANA”, “BC”]
        X	JSON Array 형태
        payment_method_type	결제 수단 제한(없을 경우 전체)
        CARD, MONEY 중 하나	X	String
        install_month	카드할부개월수. 0~12(개월) 사이의 값	X	Integer
        custom_json	결제화면에 보여주고 싶은 custom message. 사전협의가 필요한 값
        ex) iOS에서 사용자 인증 완료 후 가맹점 앱으로 자동 전환 하는 방법(iOS만 예외 처리, 안드로이드 동작안함)
        - 다음과 같이 return_custom_url key 정보에 앱스킴을 넣어서 전송. "return_custom_url":"kakaotalk://"	X	JSON Map 형태로 key, value 모두 String
     */
    @FormUrlEncoded
    @POST(AppConst.API_KAKAO_PAY_READY)
    fun transactionReady(
        @Field(AppConst.API_FIELD_CID) cid: String?,
        @Field(AppConst.API_FIELD_CID_SECRET) cid_secret: String? = null,
        @Field(AppConst.API_FIELD_PARTNER_ORDER_ID) partner_order_id: String?,
        @Field(AppConst.API_FIELD_PARTNER_USER_ID) partner_user_id: String?,
        @Field(AppConst.API_FIELD_ITEM_NAME) item_name: String?,
        @Field(AppConst.API_FIELD_ITEM_CODE) item_code: String? = null,
        @Field(AppConst.API_FIELD_QUANTITY) quantity: String?,
        @Field(AppConst.API_FIELD_TOTAL_AMOUNT) total_amount: String?,
        @Field(AppConst.API_FIELD_TAX_FREE_AMOUNT) tax_free_amount: String?,
        @Field(AppConst.API_FIELD_VAT_AMOUNT) vat_amount: String? = null,
        @Field(AppConst.API_FIELD_APPROVAL_URL) approval_url: String?,
        @Field(AppConst.API_FIELD_CANCEL_URL) cancel_url: String?,
        @Field(AppConst.API_FIELD_FAIL_URL) fail_url: String?,
        @Field(AppConst.API_FIELD_AVAILABLE_CARDS) available_cards: String? = null,
        @Field(AppConst.API_FIELD_PAYMENT_METHOD_TYPE) payment_method_type: String? = null,
        @Field(AppConst.API_FIELD_INSTALL_MONTH) install_month: String? = null,
        @Field(AppConst.API_FIELD_CUSTOM_JSON) custom_json: String? = null
    ): Single<PayReadyModel>

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
    @FormUrlEncoded
    @POST(AppConst.API_KAKAO_PAY_APPROVE)
    fun transactionApprove(
        @Field(AppConst.API_FIELD_CID) cid: String?,
        @Field(AppConst.API_FIELD_CID_SECRET) cid_secret: String? = null,
        @Field(AppConst.API_FIELD_TID) tid: String?,
        @Field(AppConst.API_FIELD_PARTNER_ORDER_ID) partner_order_id: String?,
        @Field(AppConst.API_FIELD_PARTNER_USER_ID) partner_user_id: String?,
        @Field(AppConst.API_FIELD_PG_TOKEN) pg_token: String?,
        @Field(AppConst.API_FIELD_PAYLOAD) payload: String? = null,
        @Field(AppConst.API_FIELD_TOTAL_AMOUNT) total_amount: String? = null
    ): Single<PayApproveModel>
}