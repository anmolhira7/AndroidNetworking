package com.example.globofly.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {

    //For another url we don't pass message instead we pass complete url
    //@Url used for another url
    @GET
    fun getMessages(@Url anotherUrl: String): Call<String>

}