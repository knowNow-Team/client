package com.example.konwnow.utils

import retrofit2.http.DELETE

object Constants {
    const val TAG: String = "로그"
    const val RESULT_OK: Int = 200
    const val RESULT_CANCEL: Int = 400
}

object API{
    const val BASE_USER_URL : String = "http://www.apis.user.mjuknownow.com/"
    const val BASE_WORD_URL : String = "http://www.apis.word.mjuknownow.com/"
    const val GET_TEST : String = "v1/tests"
    const val GET_TEST_ONE : String = "v1/tests/{id}"
    const val CREATE_TEST : String = "v1/tests"
    const val GET_WORDS : String = "v1/wordbooks/words"
    const val PUT_WORDS : String = "v1/wordbooks/{wordbookId}/words"
    const val WORD_FROM_IMAGE : String = "/image/text"
    const val WORD_FROM_SENTENCE : String = "/sentence/text"
    const val WORD_SCRAP : String = "v1/words/scrap"

}

object LOGIN{
    const val RC_SIGN_UP : Int = 1001
    const val RC_LOGOUT : Int = 1003

    const val CREATE_USERS : String = "v1/users"
    const val LOGIN : String = "v1/google/login"
    const val RE_LOGIN : String = "v1/login"
    const val PUT_NICKNAME : String = "v1/users/{userId}"
    const val PUT_USER_MESSAGE : String = "v1/users/{userId}/profile"

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
    const val GET_DETAIL_WORD : String = "v1/wordbooks/optionwords"
    const val DELETE_WORDBOOK : String = "v1/wordbooks/{wordbookId}"
}

object TEST{
    const val TEST_START : Int = 123
    const val TEST_LOG : Int = 321
}

object HOMEWORD{
    const val PUT_FILTER : String = "v1/wordbooks/{wordbookId}/{wordId}"
    const val MOVE_TRASH : String = "v1/wordbooks/{wordbookId}/words/{wordId}"
    const val DELETE_REAL : String = "v1/wordbooks/trashwordbooks/{wordId}"

    object FILTER{
        const val doNotKnow : String ="doNotKnow"
        const val confused : String = "confused"
        const val memorized : String = "memorized"
        const val all : String = "all"
    }
    object ORDER {
        const val ASC : String = "ASC"
        const val DESC : String = "DESC"
        const val NEWEST : String ="NEWEST"
        const val RANDOM : String ="랜덤"
    }
}

object FRIEND{
    const val GET_FRIEND : String = "v1/friends"
    const val GET_RANK : String = "v1/friends/rank"
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
