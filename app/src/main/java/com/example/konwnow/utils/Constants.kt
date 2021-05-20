package com.example.konwnow.utils

object Constants {
    const val TAG: String = "로그"
}

object API{
    const val BASE_URL : String = "https://www.apis.word.mjuknownow.com/"
    const val CLIENT_ID : String = "4NvOa3hwj74vdCMStLhnvwJrt0dw4_HjCv_2MJzLd6Y"
    const val GET_TEST: String = "v1/tests"
    const val SEARCH_USERS: String = "search/users"
}
enum class RESPONSE_STATE{
    OK,
    FAIL
}


object  ALARM{
    const val NOTIFICATION_ID = 33
    const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    val REPLY_KEY = "reply"
    val REPLY_LABEL = "단어 입력" // Action 에 표시되는 Label
}
