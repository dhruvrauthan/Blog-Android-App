package com.example.blogapp.remote

import com.example.blogapp.beans.UserPost
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    @POST("/add")
    fun addPost(
        @Body map: HashMap<String, String>
    ): Call<Void>

    @GET("/posts")
    fun getPosts(): Call<ArrayList<UserPost>>

    @PUT("/update")
    fun editPost(
        @Body map: HashMap<String, String>
    ): Call<Void>

    @HTTP(method = "DELETE", path="/delete", hasBody = true)
    fun deletePost(
        @Body map: HashMap<String, String>
    ): Call<Void>

}