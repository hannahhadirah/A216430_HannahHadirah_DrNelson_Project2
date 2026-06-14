package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a216430_hannahhadirah_drnelson_project2.data.firestore.DonatedFood
import com.example.a216430_hannahhadirah_drnelson_project2.data.local.FoodEntity
import com.example.a216430_hannahhadirah_drnelson_project2.data.repository.DonationRepository
import com.example.a216430_hannahhadirah_drnelson_project2.data.repository.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.a216430_hannahhadirah_drnelson_project2.data.remote.Meal
import com.example.a216430_hannahhadirah_drnelson_project2.data.remote.RetrofitInstance
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.a216430_hannahhadirah_drnelson_project2.data.repository.RecipeRepository

class FoodViewModel(
    private val foodRepository: FoodRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    val foodList: StateFlow<List<FoodEntity>> =
        foodRepository.allFoods.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val donationRepository = DonationRepository()

    private val _recipes = MutableStateFlow<List<Meal>>(emptyList())
    val recipes: StateFlow<List<Meal>> = _recipes

    private val _selectedRecipe = MutableStateFlow<Meal?>(null)
    val selectedRecipe: StateFlow<Meal?> = _selectedRecipe

    fun addFood(name: String, expiryDate: String) {
        viewModelScope.launch {
            foodRepository.insertFood(
                FoodEntity(
                    name = name,
                    expiryDate = expiryDate
                )
            )
        }
    }

    fun deleteFood(food: FoodEntity) {
        viewModelScope.launch {
            foodRepository.deleteFood(food)
        }
    }

    fun updateFood(food: FoodEntity) {
        viewModelScope.launch {
            foodRepository.updateFood(food)
        }
    }

    fun getDaysLeft(expiryDate: String): Long {
        return try {
            ChronoUnit.DAYS.between(
                LocalDate.now(),
                LocalDate.parse(expiryDate.trim())
            )
        } catch (e: Exception) {
            -999L
        }
    }

    fun getExpiringSoon(): List<FoodEntity> {
        return foodList.value.filter { food ->
            val
                    daysLeft = getDaysLeft(food.expiryDate)
            daysLeft in 0..3
        }
    }

    fun donateFood(name: String, expiryDate: String) {
        donationRepository.donateFood(
            DonatedFood(
                name = name,
                expiryDate = expiryDate
            )
        )
    }

    fun fetchRecipes(ingredient: String) {
        viewModelScope.launch {
            try {
                val response = recipeRepository.getRecipes(ingredient)
                _recipes.value = response.meals.orEmpty()
            } catch (e: Exception) {
                _recipes.value = emptyList()
            }
        }
    }

    fun fetchRecipeDetails(id: String) {
        viewModelScope.launch {
            try {
                val response = recipeRepository.getRecipeDetails(id)
                _selectedRecipe.value = response.meals?.firstOrNull()
            } catch (e: Exception) {
                _selectedRecipe.value = null
            }
        }
    }

}