package com.example.blogapp.feature.addPost

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

class AddPostViewModel : ViewModel() {

    private lateinit var mRetrofit: Retrofit
    private lateinit var mRetrofitInterface: RetrofitInterface
    private val mBaseUrl = "https://blog-app-dr.herokuapp.com"

    fun initRetrofit() {
        mRetrofit =
            Retrofit.Builder().baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create())
                .build()

        mRetrofitInterface = mRetrofit.create(RetrofitInterface::class.java)
    }

    fun addPost(userPost: UserPost): MutableLiveData<Boolean> {
        val successLiveData = MutableLiveData<Boolean>()
        val map = HashMap<String, String>()

        map["_id"] = userPost._id
        map["title"] = userPost.title
        map["content"] = userPost.content
        map["author"] = userPost.author

        Log.v("add", map.toString())

        val call: Call<Void> = mRetrofitInterface.addPost(map)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    successLiveData.value = true
                    Log.v("add", "success")
                } else if (response.code() == 400) {
                    successLiveData.value = false
                    Log.v("add", "error 400: " + response.body())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                successLiveData.value = false
                Log.v("add", "failure: " + t.message)
            }
        })

        return successLiveData
    }

}