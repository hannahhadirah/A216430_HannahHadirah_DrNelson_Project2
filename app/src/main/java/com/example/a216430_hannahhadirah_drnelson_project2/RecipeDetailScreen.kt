package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    mealId: String,
    viewModel: FoodViewModel
) {

    val recipe by viewModel.selectedRecipe.collectAsState()

    LaunchedEffect(mealId) {
        viewModel.fetchRecipeDetails(mealId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        AppHeader(
            title = "Recipe Detail",
            subtitle = "Full cooking guide",
            showProfile = false,
            showBack = true,
            onBackClick = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (recipe == null) {
            Text("Loading recipe...")
            return
        }

        // access
        Text(
            text = recipe?.strMeal ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("🥗 Ingredients", fontWeight = FontWeight.Bold)

        val ingredients = listOfNotNull(
            recipe?.strIngredient1,
            recipe?.strIngredient2,
            recipe?.strIngredient3,
            recipe?.strIngredient4,
            recipe?.strIngredient5,
            recipe?.strIngredient6,
            recipe?.strIngredient7,
            recipe?.strIngredient8
        )

        ingredients.forEach {
            if (it.isNotBlank()) {
                Text("• $it")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("📖 Instructions", fontWeight = FontWeight.Bold)

        Text(recipe?.strInstructions ?: "No instructions available")
    }
}