package com.example.blogapp.feature.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blogapp.remote.RetrofitInterface
import com.example.blogapp.beans.UserPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    var mUserPostsLiveData: MutableLiveData<ArrayList<UserPost>> = MutableLiveData()

    private lateinit var mRetrofit: Retrofit
    private lateinit var mRetrofitInterface: RetrofitInterface
    private val mBaseUrl = "https://blog-app-dr.herokuapp.com"

    fun initRetrofit() {
        mRetrofit =
            Retrofit.Builder().baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create())
                .build()

        mRetrofitInterface = mRetrofit.create(RetrofitInterface::class.java)
    }

    fun getPosts(): MutableLiveData<Boolean> {
        val successLiveData = MutableLiveData<Boolean>()

        val call: Call<ArrayList<UserPost>> = mRetrofitInterface.getPosts()
        call.enqueue(object : Callback<ArrayList<UserPost>> {
            override fun onResponse(
                call: Call<ArrayList<UserPost>>,
                response: Response<ArrayList<UserPost>>
            ) {
                if (response.code() == 200) {
                    successLiveData.value = true

                    val userPostList = response.body() as ArrayList<UserPost>
                    mUserPostsLiveData.value = userPostList
                } else if (response.code() == 400) {
                    successLiveData.value = false
                    Log.v("getposts", "error 400")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserPost>>, t: Throwable) {
                successLiveData.value = false
                Log.v("getposts", "failure: " + t.message)
            }
        })

        return successLiveData
    }

}