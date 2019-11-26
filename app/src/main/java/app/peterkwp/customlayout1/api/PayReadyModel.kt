package app.peterkwp.customlayout1.api

import com.google.gson.annotations.SerializedName


/**
    tid	결제 고유 번호. 20자	String
    next_redirect_app_url	요청한 클라이언트가 모바일 앱일 경우 해당 url을 통해 카카오톡 결제페이지를 띄움	String
    next_redirect_mobile_url	요청한 클라이언트가 모바일 웹일 경우 해당 url을 통해 카카오톡 결제페이지를 띄움	String
    next_redirect_pc_url	요청한 클라이언트가 pc 웹일 경우 redirect. 카카오톡으로 TMS를 보내기 위한 사용자입력화면이으로 redirect	String
    android_app_scheme	카카오페이 결제화면으로 이동하는 안드로이드 앱스킴	String
    ios_app_scheme	카카오페이 결제화면으로 이동하는 iOS 앱스킴	String
    created_at	결제 준비 요청 시간	Datetime
 */
data class PayReadyModel(
    @SerializedName("tid") val tid: String?,
    @SerializedName("next_redirect_app_url") val next_redirect_app_url: String?,
    @SerializedName("next_redirect_mobile_url") val next_redirect_mobile_url: String?,
    @SerializedName("next_redirect_pc_url") val next_redirect_pc_url: String?,
    @SerializedName("android_app_scheme") val android_app_scheme: String?,
    @SerializedName("ios_app_scheme") val ios_app_scheme: String?,
    @SerializedName("created_at") val created_at: String?
)

data class PayReadyModel2(@SerializedName("P_STATUS") val status: String?)