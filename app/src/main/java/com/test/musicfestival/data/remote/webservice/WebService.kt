package com.test.musicfestival.data.remote.webservice


import retrofit2.Response
import retrofit2.http.GET

interface WebService {
    @GET("festivals")
    suspend fun getFestivals(): Response<String>
}
