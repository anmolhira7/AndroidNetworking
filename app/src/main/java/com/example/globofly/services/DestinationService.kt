package com.example.globofly.services

import com.example.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.DELETE

interface DestinationService {

    /*@Headers("x-device-type: Android", "x-foo:bar")*/
    @GET("destination")
    fun getDestinationList(
        @QueryMap filter: HashMap<String, String>,
     /*   @Header("Accept-Language") language: String*/
    ): Call<List<Destination>>

    /*http://base-url/destination/47*/
    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination): Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") desc: String,
        @Field("country") country: String
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>
}