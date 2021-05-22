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

    var testLogList : MutableLiveData<ArrayList<TestLog.TestLogData>> = MutableLiveData()
    var testDetails : MutableLiveData<TestLog.TestDetails> = MutableLiveData()
    var createTestLog : MutableLiveData<TestLog.TestCreateResponse> = MutableLiveData()
    var testLogs: ArrayList<TestLog.TestLogData> = ArrayList()


    fun getDataObserver() : MutableLiveData<ArrayList<TestLog.TestLogData>>{
        return testLogList
    }

    fun getTestDetailObserver() : MutableLiveData<TestLog.TestDetails>{
        return testDetails
    }

    fun getTestCreateResponseObserver() : MutableLiveData<TestLog.TestCreateResponse>{
        return createTestLog
    }

    //input받는 값에 따라 live로 데이터를 호출해주는 부분
    fun getTest(loginToken: String){
        val instance = RetrofitClient.getWordClient()?.create(TestAPI::class.java)
        val call = instance?.getTestLog(loginToken)

        call?.enqueue(object : Callback<TestLog.TestInfo> {
            override fun onResponse(
                call: Call<TestLog.TestInfo>,
                response: Response<TestLog.TestInfo>
            ) {
                if (response.isSuccessful()) {
//                    Log.d("viewmodel", response.body()?.data.toString())
                    Log.d("viewmodel",testLogList.toString())
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
                Log.d("getDetail",t.message!! )
            }

        })
    }

    fun getTestById(loginToken: String,testId: String){
        val instance = RetrofitClient.getWordClient()?.create(TestAPI::class.java)
        val call = instance?.getTestLogDetail(loginToken,testId)

        call?.enqueue(object : Callback<TestLog.TestDetailResponse> {
            override fun onResponse(
                call: Call<TestLog.TestDetailResponse>,
                response: Response<TestLog.TestDetailResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("viewmodel", response.body()?.data.toString())
                    //업데이트 시켜주기.
                    testDetails.postValue(response.body()?.data)
                } else { // code == 400
                    Log.d("viewmodel", "실패")
                }
            }

            override fun onFailure(call: Call<TestLog.TestDetailResponse>, t: Throwable) {
//                Log.d("getDetail",t.message!! )
            }

        })
    }
    fun add(item: TestLog.TestLogData) {
        testLogs.add(item)
        testLogList.setValue(testLogs)
    }


    fun postTestLog(
        idtoken: String,
        correct: Int,
        difficulty: String,
        filter: List<String>,
        score: Int,
        testerId: Int,
        wordTotalCount: Int,
        wordbooks: List<String>,
        words: List<Quiz.TotalQuiz>
    ){
        val instance = RetrofitClient.getWordClient()?.create(TestAPI::class.java)
        val request = TestLog.TestRequestBody(
            correct,
            difficulty,
            filter,
            score,
            testerId,
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
                    createTestLog.postValue(testCreateResponse.body())
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
