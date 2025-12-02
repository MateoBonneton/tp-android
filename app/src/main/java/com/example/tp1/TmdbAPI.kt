package com.example.tp1

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


    interface TmbdApi {
        @GET("trending/movie/week")
        suspend fun lastMovie(@Query ("api_key") api_key: String): TrendingMovies
    }


