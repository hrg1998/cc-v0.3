package com.crossclassify.examlpeapp.model


data class CheckAccountInputModelForDev(
    val account: Account
)

data class Account(
    val email:String
)

data class CheckAccountResponseModelForDev(
    val _id:String="",
    val _status: String = "",
    val status: String = "",
    val isBlocked: Boolean
)


