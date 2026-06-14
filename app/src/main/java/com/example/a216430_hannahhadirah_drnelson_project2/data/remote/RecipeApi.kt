package com.example.a216430_hannahhadirah_drnelson_project2.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("filter.php")
    suspend fun getRecipesByIngredient(
        @Query("i") ingredient: String
    ): RecipeResponse

    @GET("lookup.php")
    suspend fun getRecipeDetails(
        @Query("i") id: String
    ): RecipeResponse
}