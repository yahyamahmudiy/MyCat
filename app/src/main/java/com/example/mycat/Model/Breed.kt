package com.example.mycat.Model

import com.google.gson.annotations.SerializedName

data class Breed(

    @field:SerializedName("Breed")
    val breed: List<BreedItem?>? = null

)

data class BreedItem(

    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("name")
    val name: String? = null

)
