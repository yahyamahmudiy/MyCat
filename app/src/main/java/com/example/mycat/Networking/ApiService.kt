package com.example.mycat.Networking

import com.example.mycat.Model.ResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("images/search?limit=50&page=1&order=Desc")
    fun getAll():Call<ArrayList<ResponseItem>>

    @GET("images")
    fun getMyCats(
        @Query("page") page: Int,
        @Query("limit") per_page: Int = 20)
    :Call<ArrayList<ResponseItem>>

    //@POST("images/upload")
    //fun makePhoto(@Body file:)

}