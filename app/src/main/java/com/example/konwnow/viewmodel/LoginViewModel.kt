package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import retrofit2.Callback
import com.example.konwnow.data.remote.retrofit.api.LoginAPi
import retrofit2.Call
import retrofit2.Response

class LoginViewModel : ViewModel() {

    var loginList : MutableLiveData<Users.SignUpBody>

    init {
        loginList = MutableLiveData()
    }

    fun getDataObserver() : MutableLiveData<Users.SignUpBody>{
        return loginList
    }

    //input받는 값에 따라 live로 데이터를 호출해주는 부분
    fun postLogin(token : String, nickname: String){
        val instance = RetrofitClient.getClient()?.create(LoginAPi::class.java)
        val postSignUp = Users.SignUpBody(token,nickname)
        val call = instance?.postSignUp(token,postSignUp)

        call?.enqueue(object : Callback<Users.SignUpBody>{
            override fun onResponse(call: Call<Users.SignUpBody>, response: Response<Users.SignUpBody>) {
                Log.d("viewmodel","success")

                //업데이트 시켜주기.
                loginList.postValue(response.body())
            }

            override fun onFailure(call: Call<Users.SignUpBody>, t: Throwable) {
                Log.d("viewmodel","fail")
            }

        })
    }
}