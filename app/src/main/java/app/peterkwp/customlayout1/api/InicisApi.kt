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
}