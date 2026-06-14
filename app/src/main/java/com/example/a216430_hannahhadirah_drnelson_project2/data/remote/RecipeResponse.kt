package com.example.a216430_hannahhadirah_drnelson_project2.data.remote

data class RecipeResponse(
    val meals: List<Meal>? = null
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,

    val strInstructions: String? = null,

    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null,
    val strIngredient9: String? = null,
    val strIngredient10: String? = null
)