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

    var signUpList : MutableLiveData<Users.SingUpResponseBody>
    var loginList : MutableLiveData<Users.LoginResponseBody>


    init {
        signUpList = MutableLiveData()
        loginList = MutableLiveData()
    }

    fun getSignUpDataObserver() : MutableLiveData<Users.SingUpResponseBody>{
        return signUpList
    }

    fun getLoginDataObserver() : MutableLiveData<Users.LoginResponseBody>{
        return loginList
    }


    fun postSignUp(idtoken: String, nickname: String){
        val instance = RetrofitClient.getUserClient()?.create(LoginAPi::class.java)
        val postSignUp = Users.SignUpBody(nickname)
        val call = instance?.postSignUp(idtoken, postSignUp)

        call?.enqueue(object : Callback<Users.SingUpResponseBody> {
            override fun onResponse(
                call: Call<Users.SingUpResponseBody>,
                singUpResponse: Response<Users.SingUpResponseBody>
            ) {
                Log.d("viewmodel", "success")

                //업데이트 시켜주기.
                signUpList.postValue(singUpResponse.body())
            }

            override fun onFailure(call: Call<Users.SingUpResponseBody>, t: Throwable) {
                Log.d("viewmodel", "fail")
            }

        })
    }

    fun postGoogleLogin(idtoken: String){
        val instance = RetrofitClient.getUserClient()?.create(LoginAPi::class.java)
        val call = instance?.postGoogleLogin(idtoken)

        call?.enqueue(object : Callback<Users.LoginResponseBody> {
            override fun onResponse(
                call: Call<Users.LoginResponseBody>, response: Response<Users.LoginResponseBody>
            ) {
                loginList.postValue(response.body())
            }

            override fun onFailure(call: Call<Users.LoginResponseBody>, t: Throwable) {
            }

        })
    }

}