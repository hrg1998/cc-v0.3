package com.crossclassify.examlpeapp.model



//TODO: Change model
data class CheckAccountInputModel(
    val account: String,
)
data class CheckAccountInputModelForDev(
    val account: Account
)
data class Account(
    val email:String
)
data class CheckAccountResponseModel(
    val _id: String = "",
    val _status: String = "",
    val _error: Error? = null,
    val status: String = "",
    val decision: String? = null
)

data class CheckAccountResponseModelForDev(
    val _id:String="",
    val _status: String = "",
    val _error: ErrorDev? = null,
    val status: String = "",
    val isBlocked: Boolean
)

data class Error(
    val code: Int,
    val message: Message
)
data class ErrorDev(
    val code: Int,
    val massage: MessageDev
)

data class Message(
    val _id: String = "",
    val decision: String? = "",
    val status: String = ""
)
data class MessageDev(
    val _id: String = "",
    val isBlocked: Boolean,
    val status: String = ""
)
