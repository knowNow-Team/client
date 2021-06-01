package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.LoginAPi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    var signUpList : MutableLiveData<Users.UserResponseBody>
    var loginList : MutableLiveData<Users.UserResponseBody>
    var putMessgeResponse : MutableLiveData<Users.UserResponseBody> = MutableLiveData()


    init {
        signUpList = MutableLiveData()
        loginList = MutableLiveData()
    }

    fun getSignUpDataObserver() : MutableLiveData<Users.UserResponseBody>{
        return signUpList
    }

    fun getLoginDataObserver() : MutableLiveData<Users.UserResponseBody>{
        return loginList
    }

    fun putMessageObserver() : MutableLiveData<Users.UserResponseBody>{
        return putMessgeResponse
    }


    fun postSignUp(idtoken: String, nickname: String){
        val instance = RetrofitClient.getUserClient()?.create(LoginAPi::class.java)
        val postSignUp = Users.SignUpBody(nickname)
        val call = instance?.postSignUp(idtoken, postSignUp)

        call?.enqueue(object : Callback<Users.UserResponseBody> {
            override fun onResponse(
                call: Call<Users.UserResponseBody>,
                userResponse: Response<Users.UserResponseBody>
            ) {
                Log.d("viewmodel", "success")

                //업데이트 시켜주기.
                signUpList.postValue(userResponse.body())
            }

            override fun onFailure(call: Call<Users.UserResponseBody>, t: Throwable) {
                Log.d("viewmodel", "fail")
            }

        })
    }

    fun postGoogleLogin(idtoken: String){
        val instance = RetrofitClient.getUserClient()?.create(LoginAPi::class.java)
        val call = instance?.postGoogleLogin(idtoken)

        call?.enqueue(object : Callback<Users.UserResponseBody> {
            override fun onResponse(
                call: Call<Users.UserResponseBody>, response: Response<Users.UserResponseBody>
            ) {
                Log.d("viewmodel", "success")
                loginList.postValue(response.body())
            }

            override fun onFailure(call: Call<Users.UserResponseBody>, t: Throwable) {
            }

        })
    }

    fun putuserMessage(token : String, userId : Int, meassge : String){
        val instance = RetrofitClient.getUserClient()?.create(LoginAPi::class.java)
        val call = instance?.putUserMessage(token,userId,Users.PutMessageBody(meassge))

        call?.enqueue(object : Callback<Users.UserResponseBody> {
            override fun onResponse(
                call: Call<Users.UserResponseBody>,
                response: Response<Users.UserResponseBody>
            ) {
                putMessgeResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Users.UserResponseBody>, t: Throwable) {
            }

        })
    }

}