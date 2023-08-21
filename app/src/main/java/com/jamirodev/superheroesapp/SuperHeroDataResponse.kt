package com.jamirodev.superheroesapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperheroItemResponse>
)

data class SuperheroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superheroImage:SuperheroImageResponse,
    @SerializedName("appearance") val appearance: Appearance
)

data class Appearance(
    @SerializedName("gender") val gender: String,
    @SerializedName("race") val race: String,
    @SerializedName("height") val height: List<String>,
    @SerializedName("weight") val weight: List<String>,
    @SerializedName("eye-color") val eyeColor: String,
    @SerializedName("hair-color") val hairColor: String
)

data class SuperheroImageResponse(@SerializedName("url") val url:String)