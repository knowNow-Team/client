package com.example.konwnow.data.retrofit

import android.util.Log
import com.example.konwnow.utils.API
import com.example.konwnow.utils.Constants.TAG
import com.example.konwnow.utils.isJsonArray
import com.example.konwnow.utils.isJsonObject
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    //클라이언트
    private  var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG,"RetrofitClient - getClient() called")

        //로깅 인터셉터 추가

        //okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
//                Log.d(TAG,"RetrofitClient - log() called / message: $message")

                when{
                    message.isJsonObject() ->
                        Log.d(TAG,JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG,JSONObject(message).toString(4))
                    else -> {
                        try{
                            Log.d(TAG,JSONObject(message).toString(4))
                        }catch (e: Exception){
                            Log.d(TAG,message)
                        }
                    }
                }
            }
        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        //loggingInterceptor 추가
        client.addInterceptor(loggingInterceptor)

        //기본 파라미터 추가
        val baseParameterInterceptor: Interceptor = (object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG,"RetrofitClient - intercept() called")

                //오리지날 리퀘스트
                val originalRequest: Request = chain.request()

                //쿼리 파라미터 추가
                val addedUrl: HttpUrl = originalRequest.url.newBuilder().addQueryParameter("client_id",API.CLIENT_ID).build()

                val finalRequest = originalRequest.newBuilder()
                                    .url(addedUrl)
                                    .method(originalRequest.method, originalRequest.body)
                                    .build()

                return chain.proceed(finalRequest)
            }
        })

        //인터셉터 추가
        client.addInterceptor(baseParameterInterceptor)
//        client.connectTimeout(10,TimeUnit.SECONDS)
//        client.readTimeout(10,TimeUnit.SECONDS)
//        client.writeTimeout(10,TimeUnit.SECONDS)
//        client.retryOnConnectionFailure(true)


        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}