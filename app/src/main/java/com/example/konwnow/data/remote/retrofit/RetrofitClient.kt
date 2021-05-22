package com.example.konwnow.data.remote.retrofit

import android.util.Log
import com.example.konwnow.data.remote.retrofit.api.TestAPI
import com.example.konwnow.utils.API
import com.example.konwnow.utils.Constants.TAG
import com.example.konwnow.utils.isJsonArray
import com.example.konwnow.utils.isJsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object{
        private  var retrofitClient: Retrofit? = null
        fun getUserClient(): Retrofit? {
            Log.d(TAG,"RetrofitClient - getClient() called")

            val baseUrl = API.BASE_USER_URL
            val client = OkHttpClient.Builder()
                .connectTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)

            val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
                override fun log(message: String) {
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
//                    val addedUrl: HttpUrl = originalRequest.url.newBuilder().addQueryParameter("client_id",API.CLIENT_ID).build()

                    val finalRequest = originalRequest.newBuilder()
//                        .url(addedUrl)
                        .method(originalRequest.method, originalRequest.body)
                        .build()

                    return chain.proceed(finalRequest)
                }
            })

            //인터셉터 추가
            client.addInterceptor(baseParameterInterceptor)

            if(retrofitClient?.baseUrl().toString() != API.BASE_USER_URL){

                    retrofitClient = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build()
            }
            return retrofitClient
        }


        fun getWordClient(): Retrofit? {
            Log.d(TAG,"RetrofitClient - getClient() called")

            val baseUrl = API.BASE_WORD_URL
            val client = OkHttpClient.Builder()
                .connectTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)

            val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
                override fun log(message: String) {
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

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            //loggingInterceptor 추가
            client.addInterceptor(loggingInterceptor)

            //기본 파라미터 추가
            val baseParameterInterceptor: Interceptor = (object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    Log.d(TAG,"RetrofitClient - intercept() called")

                    //오리지날 리퀘스트
                    val originalRequest: Request = chain.request()

                    //쿼리 파라미터 추가
//                    val addedUrl: HttpUrl = originalRequest.url.newBuilder().addQueryParameter("client_id",API.CLIENT_ID).build()

                    val finalRequest = originalRequest.newBuilder()
//                        .url(addedUrl)
                        .method(originalRequest.method, originalRequest.body)
                        .build()

                    return chain.proceed(finalRequest)
                }
            })

            //인터셉터 추가
            client.addInterceptor(baseParameterInterceptor)

            if(retrofitClient?.baseUrl().toString() != API.BASE_WORD_URL){
                retrofitClient = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build()
            }
            return retrofitClient
        }
    }

}