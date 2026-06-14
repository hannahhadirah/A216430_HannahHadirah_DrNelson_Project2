package com.example.a216430_hannahhadirah_drnelson_project2.data.repository

import com.example.a216430_hannahhadirah_drnelson_project2.data.remote.RecipeResponse
import com.example.a216430_hannahhadirah_drnelson_project2.data.remote.RetrofitInstance

class RecipeRepository {

    suspend fun getRecipes(ingredient: String): RecipeResponse {
        return RetrofitInstance.api.getRecipesByIngredient(ingredient)
    }

    suspend fun getRecipeDetails(id: String): RecipeResponse {
        return RetrofitInstance.api.getRecipeDetails(id)
    }
}
