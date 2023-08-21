package com.jamirodev.superheroesapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/YOURAPPI/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName: String): Response<SuperHeroDataResponse>

    @GET("/api/YOURAPI/{id}")
    suspend fun getSuperheroDetail(@Path("id") superheroId:String): Response<SuperHeroDetailResponse>


}
