package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName


/**
    aid	Request 고유 번호	String
    tid	결제 고유 번호	String
    cid	가맹점 코드	String
    sid	subscription id. 정기(배치)결제 CID로 결제요청한 경우 발급	String
    partner_order_id	가맹점 주문번호	String
    partner_user_id	가맹점 회원 id	String
    payment_method_type	결제 수단. CARD, MONEY 중 하나	String
    amount	결제 금액 정보	JSON Object
    card_info	결제 상세 정보(결제수단이 카드일 경우만 포함)	JSON Object
    item_name	상품 이름. 최대 100자	String
    item_code	상품 코드. 최대 100자	String
    quantity	상품 수량	Integer
    created_at	결제 준비 요청 시각	Datetime
    approved_at	결제 승인 시각	Datetime
    payload	Request로 전달한 값	String
 */
data class PayApproveModel(
    @SerializedName("aid") val aid: String,
    @SerializedName("tid") val tid: String,
    @SerializedName("sid") val sid: String,
    @SerializedName("partner_order_id") val partner_order_id: String,
    @SerializedName("payment_method_type") val payment_method_type: String,
    @SerializedName("amount") val amount: Amount,
    @SerializedName("card_info") val card_info: CardInfo,
    @SerializedName("item_name") val item_name: String,
    @SerializedName("item_code") val item_code: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("approved_at") val approved_at: String,
    @SerializedName("payload") val payload: String
)

/**
    total	전체 결제 금액	Integer
    tax_free	비과세 금액	Integer
    vat	부가세 금액	Integer
    point	사용한 포인트 금액	Integer
    discount	할인 금액	Integer
 */
data class Amount(
    @SerializedName("total") val total: String,
    @SerializedName("tax_free") val tax_free: String,
    @SerializedName("vat") val vat: String,
    @SerializedName("point") val point: String,
    @SerializedName("discount") val discount: String
)

/**
    purchase_corp	매입카드사 한글명	String
    purchase_corp_code	매입카드사 코드	String
    issuer_corp	카드발급사 한글명	String
    issuer_corp_code	카드발급사 코드	String
    kakaopay_purchase_corp	카카오페이 매입사명	String
    kakaopay_purchase_corp_code	카카오페이 매입사코드	String
    kakaopay_issuer_corp	카카오페이 발급사명	String
    kakaopay_issuer_corp_code	카카오페이 발급사코드	String
    bin	카드 BIN	String
    card_type	카드타입	String
    install_month	할부개월수	String
    approved_id	카드사 승인번호	String
    card_mid	카드사 가맹점번호	String
    interest_free_install	무이자할부 여부(Y/N)	String
    card_item_code	카드 상품 코드	String
 */
data class CardInfo(
    @SerializedName("purchase_corp") val purchase_corp: String,
    @SerializedName("purchase_corp_code") val purchase_corp_code: String,
    @SerializedName("issuer_corp") val issuer_corp: String,
    @SerializedName("issuer_corp_code") val issuer_corp_code: String,
    @SerializedName("kakaopay_purchase_corp") val kakaopay_purchase_corp: String,
    @SerializedName("kakaopay_purchase_corp_code") val kakaopay_purchase_corp_code: String,
    @SerializedName("kakaopay_issuer_corp") val kakaopay_issuer_corp: String,
    @SerializedName("kakaopay_issuer_corp_code") val kakaopay_issuer_corp_code: String,
    @SerializedName("bin") val bin: String,
    @SerializedName("card_type") val card_type: String,
    @SerializedName("install_month") val install_month: String,
    @SerializedName("approved_id") val approved_id: String,
    @SerializedName("card_mid") val card_mid: String,
    @SerializedName("interest_free_install") val interest_free_install: String,
    @SerializedName("card_item_code") val card_item_code: String
)