package com.crossclassify.examlpeapp.model

import com.google.gson.annotations.SerializedName


data class CheckAccountInputModel(
    val account: Account
)

data class Account(
    val email:String
)

data class CheckAccountResponseModel(
    val _id:String="",
    val _status: String = "",
    val status: String = "",
    val isBlocked: Boolean,
    val decisionDetails: DecisionDetails?= null
)


data class DecisionDetails (

    @SerializedName("decisionOnDevice"  ) var decisionOnDevice  : DecisionOnDevice?  = DecisionOnDevice(),
    @SerializedName("decisionOnAccount" ) var decisionOnAccount : DecisionOnAccount? = DecisionOnAccount()

)


data class DecisionOnAccount (

    @SerializedName("decisionId" ) var decisionId : String? = null

)

data class DecisionOnDevice (

    @SerializedName("decisionId" ) var decisionId : String? = null

)