package com.example.konwnow.utils

object Constants {
    const val TAG: String = "로그"
}

object API{
    const val BASE_URL : String = "https://api.unsplash.com/"
    const val CLIENT_ID : String = "4NvOa3hwj74vdCMStLhnvwJrt0dw4_HjCv_2MJzLd6Y"
    const val SEARCH_PHOTOS: String = "search/photos"
    const val SEARCH_USERS: String = "search/users"
}

enum class RESPONSE_STATE{
    OK,
    FAIL
}

object LOGIN {
    const val RC_SIGN_UP : Int = 1001
    const val RC_SIGN_IN : Int = 1002
    const val RC_LOGOUT : Int = 1003
}


object  ALARM{
    const val NOTIFICATION_ID = 33
    const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    val REPLY_KEY = "reply"
    val REPLY_LABEL = "단어 입력" // Action 에 표시되는 Label
}
