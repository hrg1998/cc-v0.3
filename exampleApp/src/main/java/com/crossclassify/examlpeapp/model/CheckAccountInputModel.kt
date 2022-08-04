package com.crossclassify.examlpeapp.model


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
    val isBlocked: Boolean
)


