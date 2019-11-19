package app.peterkwp.customlayout1.feature

object AppConst {

    private const val V1 = "v1"
    private const val V2 = "v2"
    private const val PAYMENT = "payment"

    /**
     * Kakao API
     */
    const val KAKAO_API = "kakaoapi"
    const val KAKAO_API_ADDR = "https://dapi.kakao.com"

    // search image
    const val API_KAKAO_SEARCH_IMAGE = "/$V2/search/image"

    /**
     * Kakao Pay API
     */
    const val KAKAO_PAY_API = "kakaopay"
    const val KAKAO_PAY_API_ADDR = "https://kapi.kakao.com"

    // ready
    const val API_KAKAO_PAY_READY = "/$V1/$PAYMENT/ready"
    // approve
    const val API_KAKAO_PAY_APPROVE = "/$V1/$PAYMENT/approve"

    /**
     * Field
     */

    // cid	가맹점 코드. 10자.	O	String
    const val API_FIELD_CID = "cid"

    // 결제 고유번호. 결제준비 API의 응답에서 얻을 수 있음	O	String
    const val API_FIELD_CID_SECRET = "cid_secret"

    // cid	가맹점 코드. 10자.	O	String
    const val API_FIELD_TID = "tid"

    // 가맹점 주문번호. 최대 100자	O	String
    const val API_FIELD_PARTNER_ORDER_ID = "partner_order_id"

    // 가맹점 회원 id. 최대 100자	O	String
    const val API_FIELD_PARTNER_USER_ID = "partner_user_id"

    // 상품명. 최대 100자	O	String
    const val API_FIELD_ITEM_NAME = "item_name"

    // 상품코드. 최대 100자	X	String
    const val API_FIELD_ITEM_CODE = "item_code"

    // 상품 수량	O	Integer
    const val API_FIELD_QUANTITY = "quantity"

    // 상품 총액	O	Integer
    const val API_FIELD_TOTAL_AMOUNT = "total_amount"

    // 상품 비과세 금액	O	Integer
    const val API_FIELD_TAX_FREE_AMOUNT = "tax_free_amount"

    // 상품 부가세 금액(안보낼 경우 (상품총액 - 상품 비과세 금액)/11 : 소숫점이하 반올림)	X	Integer
    const val API_FIELD_VAT_AMOUNT = "vat_amount"

    // 결제 성공시 redirect url. 최대 255자	O	String
    const val API_FIELD_APPROVAL_URL = "approval_url"

    // 결제 취소시 redirect url. 최대 255자	O	String
    const val API_FIELD_CANCEL_URL = "cancel_url"

    // 결제 실패시 redirect url. 최대 255자	O	String
    const val API_FIELD_FAIL_URL = "fail_url"

    /**
     * 카드사 제한 목록(없을 경우 전체)
     * 현재 SHINHAN, KB, HYUNDAI, LOTTE, SAMSUNG, NH, BC, HANA, CITI, KAKAOBANK, KAKAOPAY, WOORI, GWANGJU, SUHYUP, SHINHYUP, JEONBUK, JEJU, SC 지원.
     * ex) [“HANA”, “BC”] X JSON Array 형태
     */
    const val API_FIELD_AVAILABLE_CARDS = "available_cards"

    /**
     * 결제 수단 제한(없을 경우 전체)
     * CARD, MONEY 중 하나	X	String
     */
    const val API_FIELD_PAYMENT_METHOD_TYPE = "payment_method_type"

    // 카드할부개월수. 0~12(개월) 사이의 값	X	Integer
    const val API_FIELD_INSTALL_MONTH = "install_month"

    /**
     * 결제화면에 보여주고 싶은 custom message. 사전협의가 필요한 값
     * ex) iOS에서 사용자 인증 완료 후 가맹점 앱으로 자동 전환 하는 방법(iOS만 예외 처리, 안드로이드 동작안함)
     * - 다음과 같이 return_custom_url key 정보에 앱스킴을 넣어서 전송. "return_custom_url":"kakaotalk://" X JSON Map 형태로 key, value 모두 String
    */
    const val API_FIELD_CUSTOM_JSON = "custom_json"

    // 결제승인 요청을 인증하는 토큰. 사용자가 결제수단 선택 완료시 approval_url로 redirection해줄 때 pg_token을 query string으로 넘겨줌	O	String
    const val API_FIELD_PG_TOKEN = "pg_token"

    // 해당 Request와 매핑해서 저장하고 싶은 값. 최대 200자	X	String
    const val API_FIELD_PAYLOAD = "payload"
}