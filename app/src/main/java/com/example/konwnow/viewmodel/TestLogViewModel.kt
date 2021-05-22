package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.TestAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestLogViewModel : ViewModel() {

    var TestLogList : MutableLiveData<ArrayList<TestLog.TestLogData>> = MutableLiveData()
    var CreateTestLog : MutableLiveData<TestLog.TestCreateResponse> = MutableLiveData()
    var TestLogs: ArrayList<TestLog.TestLogData> = ArrayList()


    fun getDataObserver() : MutableLiveData<ArrayList<TestLog.TestLogData>>{
        return TestLogList
    }

    fun getTestCreateResponseObserver() : MutableLiveData<TestLog.TestCreateResponse>{
        return CreateTestLog
    }

    //input받는 값에 따라 live로 데이터를 호출해주는 부분
    fun getTest(){
        val instance = RetrofitClient.getWordClient()?.create(TestAPI::class.java)
        val call = instance?.getTestLog(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm93bm93IiwiZXhwIjoxNjIzODc1ODU4LCJ1c2VyIjoiYWF" +
                    "AYWEuY29tIiwidXNlcklkIjoxLCJpYXQiOjE2MjEyODM4NTh9.poh-Tq4SyOrBafBHvTN-Y-c9deRvzasJ7Jx-0_FiUfU"
        )

        call?.enqueue(object : Callback<TestLog.TestInfo> {
            override fun onResponse(
                call: Call<TestLog.TestInfo>,
                response: Response<TestLog.TestInfo>
            ) {
                if (response.isSuccessful()) {
                    Log.d("viewmodel", response.body()?.data.toString())
                    //업데이트 시켜주기.
//                    TestLogList.postValue(response.body()?.data)
                    for (it in response.body()?.data!!) {
                        add(it)
                    }
                } else { // code == 400
                    Log.d("viewmodel", "실패")
                }
            }

            override fun onFailure(call: Call<TestLog.TestInfo>, t: Throwable) {
                Log.d("viewmodel", "응답 실패")
            }

        })
    }
    fun add(item: TestLog.TestLogData) {
        TestLogs.add(item)
        TestLogList.setValue(TestLogs)
    }



//    @SerializedName("correctAnswerCount")
//    val correctAnswerCount: Int,
//    @SerializedName("difficulty")
//    val difficulty: String,
//    @SerializedName("filter")
//    val filter: List<String>,
//    @SerializedName("score")
//    val score: Int,
//    @SerializedName("testerId")
//    val testerId: Int,
//    @SerializedName("wordTotalCount")
//    val wordTotalCount: Int,
//    @SerializedName("wordbooks")
//    val wordbooks: List<String>,
//    @SerializedName("Quiz")
//    val words: List<Quiz>

    fun postTestLog(
        idtoken: String,
        correct: Int,
        difficulty: String,
        filter: List<String>,
        score: Int,
        wordTotalCount: Int,
        wordbooks: List<String>,
        words: List<Quiz>
    ){
        val instance = RetrofitClient.getWordClient()?.create(TestAPI::class.java)
        val request = TestLog.TestRequestBody(
            correct,
            difficulty,
            filter,
            score,
            1,
            wordTotalCount,
            wordbooks,
            words
        )
        val call = instance?.postTestLog(idtoken, request)

        call?.enqueue(object : Callback<TestLog.TestCreateResponse> {
            override fun onResponse(
                call: Call<TestLog.TestCreateResponse>,
                testCreateResponse: Response<TestLog.TestCreateResponse>
            ) {
                if (testCreateResponse.isSuccessful) {
                    //업데이트 시켜주기.
                    CreateTestLog.postValue(testCreateResponse.body())
                } else {
                    Log.d("viewmodel", "실패!!")
                }

            }

            override fun onFailure(call: Call<TestLog.TestCreateResponse>, t: Throwable) {
                Log.d("viewmodel", "fail")
            }
        })
    }
}