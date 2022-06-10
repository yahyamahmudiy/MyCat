package com.example.mycat.Networking

import com.example.mycat.Model.BreedsItem
import com.example.mycat.Model.ResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ApiService {

    @GET("breeds")
    fun getAll():Call<ArrayList<BreedsItem>>

    @GET("images")
    fun getMyCats(
        @Query("page") page: Int,
        @Query("limit") per_page: Int = 20)
    :Call<ArrayList<ResponseItem>>

    @Multipart
    @POST("images/upload")
    fun uploadFile(@Part image: MultipartBody.Part, @Part("sub_id") name: String): Call<ResponseItem>

}