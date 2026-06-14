package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage

@Composable
fun RecipeScreen(
    navController: NavController,
    foodName: String,
    viewModel: FoodViewModel
) {

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(foodName) {
        viewModel.fetchRecipes(foodName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        AppHeader(
            title = "Recipes",
            subtitle = "Smart meal suggestions",
            showProfile = false,
            showBack = true,
            onBackClick = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(16.dp))


        // header message
        Text(
            text = "🍽 Recipes to reduce food waste for: $foodName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))


        if (recipes.isEmpty()) {

            Text("Loading recipes...")

        } else {

            recipes.forEach { meal ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable {
                            meal.idMeal?.let { id ->
                                navController.navigate("recipeDetail/$id")
                            }
                        },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Column(modifier = Modifier.padding(12.dp)) {

                        AsyncImage(
                            model = meal.strMealThumb,
                            contentDescription = meal.strMeal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "🍳 ${meal.strMeal}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Tap to view full recipe →",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

fun getFoodCategory(food: String): String {

    val item = food.lowercase()

    return when {

        item.contains("milk") ||
                item.contains("cheese") ||
                item.contains("yogurt") ->
            "Dairy 🥛"

        item.contains("banana") ||
                item.contains("apple") ||
                item.contains("orange") ||
                item.contains("strawberry") ||
                item.contains("mango") ->
            "Fruit 🍎"

        item.contains("carrot") ||
                item.contains("broccoli") ||
                item.contains("spinach") ->
            "Vegetable 🥦"

        item.contains("bread") ->
            "Bakery 🍞"

        item.contains("chicken") ||
                item.contains("egg") ->
            "Protein 🍗"

        else ->
            "General Food 🍽"
    }
}
//
fun getStorageTip(food: String): String {

    val item = food.lowercase()

    return when {

        item.contains("banana") ->
            "Store at room temperature for better freshness."

        item.contains("milk") ->
            "Keep refrigerated below 4°C."

        item.contains("bread") ->
            "Store in airtight container to prevent drying."

        item.contains("apple") ->
            "Keep refrigerated to extend shelf life."

        else ->
            "Store in a cool and dry place."
    }
}