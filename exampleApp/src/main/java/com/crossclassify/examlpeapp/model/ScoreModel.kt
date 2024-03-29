
package com.crossclassify.examlpeapp.model
import com.google.gson.annotations.SerializedName
data class ScoreResponseModel(

    @SerializedName("_id"                     ) var Id                      : String?                  = null,
    @SerializedName("serviceName"             ) var serviceName             : String?                  = null,
    @SerializedName("projectId"               ) var projectId               : String?                  = null,
    @SerializedName("account"                 ) var account                 : ScoreAccount?                 = ScoreAccount(),
    @SerializedName("device"                  ) var device                  : Device?                  = Device(),
    @SerializedName("rule"                    ) var rule                    : Rule?                    = Rule(),
    @SerializedName("automaticDecisionDetail" ) var automaticDecisionDetail : AutomaticDecisionDetail? = AutomaticDecisionDetail(),
    @SerializedName("_updated"                ) var Updated                 : String?                  = null,
    @SerializedName("_created"                ) var Created                 : String?                  = null,
    @SerializedName("kind"                    ) var kind                    : String?                  = null,
    @SerializedName("_etag"                   ) var Etag                    : String?                  = null,
    @SerializedName("_links"                  ) var Links                   : Links?                   = Links()

)
data class ScoreAccount (

    @SerializedName("email" ) var email : String? = null

)
data class Exceptions (

    @SerializedName("fingerprint" ) var fingerprint : String? = null

)
data class Collection (

    @SerializedName("title" ) var title : String? = null,
    @SerializedName("href"  ) var href  : String? = null

)

data class Device (

    @SerializedName("fingerprint" ) var fingerprint : String? = null

)

data class AutomaticDecisionDetail (

    @SerializedName("isManualDecisionRequired" ) var isManualDecisionRequired : Boolean?          = null,
    @SerializedName("makeFormDecisionId"       ) var makeFormDecisionId       : String?           = null,
    @SerializedName("score"                    ) var score                    : Int?              = null,
    @SerializedName("signals"                  ) var signals                  : ArrayList<String> = arrayListOf(),
    @SerializedName("fraudServicesId"          ) var fraudServicesId          : String?           = null

)

data class Links (

    @SerializedName("self"       ) var self       : Self?       = Self(),
    @SerializedName("related"    ) var related    : Related?    = Related(),
    @SerializedName("parent"     ) var parent     : Parent?     = Parent(),
    @SerializedName("collection" ) var collection : Collection? = Collection()

)

data class Parent (

    @SerializedName("title" ) var title : String? = null,
    @SerializedName("href"  ) var href  : String? = null

)


data class ProjectId (

    @SerializedName("title" ) var title : String? = null,
    @SerializedName("href"  ) var href  : String? = null

)

data class Related (

    @SerializedName("projectId" ) var projectId : ProjectId? = ProjectId()

)

data class Rule (

    @SerializedName("isBlocked"  ) var isBlocked  : Boolean?              = null,
    @SerializedName("exceptions" ) var exceptions : ArrayList<Exceptions> = arrayListOf()

)

data class Self (

    @SerializedName("title" ) var title : String? = null,
    @SerializedName("href"  ) var href  : String? = null

)

