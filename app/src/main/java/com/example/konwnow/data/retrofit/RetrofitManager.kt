package com.example.konwnow.data.retrofit

import android.util.Log
import com.example.konwnow.utils.API
import com.example.konwnow.utils.Constants.TAG
import com.example.konwnow.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
    }
    //레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term: String = searchTerm.let{
            it
        }?: ""

        val call: Call<JsonElement> = iRetrofit?.searchPhotos(searchTerm = term)?: return

        call.enqueue(object : retrofit2.Callback<JsonElement>{
            //응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"RetrofitManager - onResponse() called / response: ${response.body()!!.asJsonObject.get("results").asJsonArray}")
                    completion(RESPONSE_STATE.OK ,response.body()!!.asJsonObject.get("results").asJsonArray.get(0).asJsonObject.get("urls").asJsonObject.get("thumb").asString)
//                response.body()!!.asJsonObject.get("result").asJsonArray
            }

            //응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"RetrofitManager - onFailure() called / t: $t")
                completion( RESPONSE_STATE.FAIL,t.toString())
            }


        })
    }
}