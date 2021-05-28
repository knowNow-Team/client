package com.example.konwnow.utils

object Constants {
    const val TAG: String = "로그"
}

object API{
    const val BASE_USER_URL : String = "http://www.apis.user.mjuknownow.com/"
    const val BASE_WORD_URL : String = "http://www.apis.word.mjuknownow.com/"
    const val GET_TEST : String = "v1/tests"
    const val GET_TEST_ONE : String = "v1/tests/{id}"
    const val CREATE_TEST : String = "v1/tests"
    const val GET_WORDS : String = "v1/wordbooks/words"
    const val POST_WORDS : String = "v1/wordbooks/{wordbook}/words"
    const val WORD_FROM_IMAGE : String = "/image/text"

}

object LOGIN{
    const val RC_SIGN_UP : Int = 1001
    const val RC_LOGOUT : Int = 1003

    const val CREATE_USERS : String = "v1/users"
    const val LOGIN : String = "v1/google/login"
    const val RE_LOGIN : String = "v1/login"
    const val GET_USER : String = "v1/users/{userId}"

    enum class LOGIN_FLAG{
        NORMAL_LOGIN,
        OTHER_LOGIN,
        NULL_LOGIN
    }
}

enum class RESPONSE_STATE{
    OK,
    FAIL
}

object WORDBOOK{
    const val WORDBOOK : String = "v1/wordbooks"
    const val TRASH_BOOK_ID : String = "trashbook"
    const val GET_TRASH_WORD : String ="v1/wordbooks/trashwordbooks"
}

object CAMERA{
//    const val CAMERA_REQUEST= 234
    const val PICK_FROM_ALBUM = 1
    const val REQUEST_IMAGE_CAPTURE = 2
}


object  ALARM{
    const val NOTIFICATION_ID = 33
    const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    val REPLY_KEY = "reply"
    val REPLY_LABEL = "단어 입력" // Action 에 표시되는 Label
}
