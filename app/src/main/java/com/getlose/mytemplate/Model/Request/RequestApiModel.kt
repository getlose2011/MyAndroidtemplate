package com.getlose.mytemplate.Model.Request

import com.google.gson.annotations.SerializedName


@Suppress("unused", "PropertyName")
class RequestApiModel {

    @SerializedName("request")
    var request: String? = null

    @SerializedName("param")
    var param: RequestApiParamModel? = null

    class RequestApiParamModel {

        @SerializedName("type")
        var type: String? = null

        @SerializedName("kind")
        var kind: String? = null

        @SerializedName("email")
        var email: String? = null
    }
}
