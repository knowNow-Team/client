package com.example.konwnow.utils

object Constants {
    const val TAG: String = "로그"
}

object API{
    const val BASE_URL : String = "https://www.apis.user.mjuknownow.com/ "
    const val CLIENT_ID : String = "4NvOa3hwj74vdCMStLhnvwJrt0dw4_HjCv_2MJzLd6Y"
    const val SEARCH_PHOTOS: String = "search/photos"
    const val SEARCH_USERS: String = "search/users"

    const val CREATE_USERS : String = "v1/users"
}

enum class RESPONSE_STATE{
    OK,
    FAIL
}

object LOGIN {
    const val RC_SIGN_UP : Int = 1001
    const val RC_LOGOUT : Int = 1003
}


object  ALARM{
    const val NOTIFICATION_ID = 33
    const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    val REPLY_KEY = "reply"
    val REPLY_LABEL = "단어 입력" // Action 에 표시되는 Label
}
