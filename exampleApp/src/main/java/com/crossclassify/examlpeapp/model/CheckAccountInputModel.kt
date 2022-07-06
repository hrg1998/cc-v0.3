package com.crossclassify.examlpeapp.model

//TODO: Change model
data class CheckAccountInputModel(
    val account: String,
)

data class CheckAccountResponseModel(
    val _id: String = "",
    val _status: String = "",
    val _error: Error? = null,
    val status: String = "",
    val decision: String? = null
)

data class Error(
    val code: Int,
    val message: Message
)

data class Message(
    val _id: String = "",
    val decision: String? = "",
    val status: String = ""
)