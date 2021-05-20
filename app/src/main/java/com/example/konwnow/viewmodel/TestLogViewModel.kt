package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import retrofit2.Callback
import com.example.konwnow.data.remote.retrofit.api.TestAPI
import retrofit2.Call
import retrofit2.Response

class TestLogViewModel : ViewModel() {

    var TestLogList : MutableLiveData<TestLog>

    init {
        TestLogList = MutableLiveData()
    }

    fun getDataObserver() : MutableLiveData<TestLog>{
        return TestLogList
    }

    //input받는 값에 따라 live로 데이터를 호출해주는 부분
    fun getTest(){
        val instance = RetrofitClient.getClient()?.create(TestAPI::class.java)
        val call = instance?.getTestLog("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm93bm93IiwiZXhwIjoxNjIzODc1ODU4LCJ1c2VyIjoiYWFAYWEuY29tIiwidXNlcklkIjoxLCJpYXQiOjE2MjEyODM4NTh9.poh-Tq4SyOrBafBHvTN-Y-c9deRvzasJ7Jx-0_FiUfU")

        call?.enqueue(object : Callback<TestLog>{
            override fun onResponse(call: Call<TestLog>, response: Response<TestLog>) {
                Log.d("viewmodel","success")

                //업데이트 시켜주기.
                TestLogList.postValue(response.body())
            }

            override fun onFailure(call: Call<TestLog>, t: Throwable) {
            }

        })
    }
}