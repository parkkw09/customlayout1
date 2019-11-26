package app.peterkwp.customlayout1.api

import app.peterkwp.customlayout1.feature.AppConst
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InicisApi {

    @FormUrlEncoded
    @POST(AppConst.API_INI_PAY_READY)
    fun transactionReady(
        @Field(AppConst.API_FIELD_INI_P_INI_PAYMENT) payment: String?,
        @Field(AppConst.API_FIELD_INI_P_MID) mid: String?,
        @Field(AppConst.API_FIELD_INI_P_OID) oid: String? = null,
        @Field(AppConst.API_FIELD_INI_P_AMT) amt: String?,
        @Field(AppConst.API_FIELD_INI_P_UNAME) uname: String?,
        @Field(AppConst.API_FIELD_INI_P_MNAME) mname: String? = null,
        @Field(AppConst.API_FIELD_INI_P_NOTI) noti: String? = null,
        @Field(AppConst.API_FIELD_INI_P_GOODS) goods: String?,
        @Field(AppConst.API_FIELD_INI_P_MOBILE) mobile: String? = null,
        @Field(AppConst.API_FIELD_INI_P_EMAIL) email: String? = null,
        @Field(AppConst.API_FIELD_INI_P_NEXT_URL) next_url: String? = null,
        @Field(AppConst.API_FIELD_INI_P_NOTI_URL) noti_url: String? = null,
        @Field(AppConst.API_FIELD_INI_P_TAX) tax: String? = null,
        @Field(AppConst.API_FIELD_INI_P_TAXFREE) taxfree: String? = null,
        @Field(AppConst.API_FIELD_INI_P_OFFER_PERIOD) offerperiod: String? = null,
        @Field(AppConst.API_FIELD_INI_P_CARD_OPTION) cardoption: String? = null,
        @Field(AppConst.API_FIELD_INI_P_ONLY_CARDCODE) cardcode: String? = null,
        @Field(AppConst.API_FIELD_INI_P_QUOTABASE) quotabase: String? = null,
        @Field(AppConst.API_FIELD_INI_P_HPP_METHOD) hpp_method: String? = null,
        @Field(AppConst.API_FIELD_INI_P_VBANK_DT) vbank_dt: String? = null,
        @Field(AppConst.API_FIELD_INI_P_VBANK_TM) vbank_tm: String? = null,
        @Field(AppConst.API_FIELD_INI_P_CHARSET) charset: String? = null,
        @Field(AppConst.API_FIELD_INI_P_RESERVED) reserved: String? = null
    ): Single<PayReadyModel2>
}