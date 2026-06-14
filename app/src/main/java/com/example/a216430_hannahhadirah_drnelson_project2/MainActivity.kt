package com.example.a216430_hannahhadirah_drnelson_project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.a216430_hannahhadirah_drnelson_project2.data.local.FoodDatabase
import com.example.a216430_hannahhadirah_drnelson_project2.data.repository.FoodRepository
import com.example.a216430_hannahhadirah_drnelson_project2.data.repository.RecipeRepository
import com.example.a216430_hannahhadirah_drnelson_project2.ui.theme.A216430_HannahHadirah_DrNelson_Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A216430_HannahHadirah_DrNelson_Lab4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FreshKeeperApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }
        }
    }
}

@Composable
fun FreshKeeperApp(modifier: Modifier = Modifier) {

//  provides
    val navController = rememberNavController()

    val context = androidx.compose.ui.platform.LocalContext.current
    val database = FoodDatabase.getDatabase(context)
    val foodRepository = FoodRepository(database.foodDao())
    val recipeRepository = RecipeRepository()

    val viewModel = remember {
        FoodViewModel(foodRepository, recipeRepository)
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

//    provides a basic app layout structure (like frame)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("addFood")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+", fontSize = 24.sp)
            }
        },

        bottomBar = {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {

                    // home navigation
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text(
                                "🏠",
                                fontSize = 18.sp
                            )
                        },
                        label = { Text("Home") }
                    )

                    // add food navigation
                    NavigationBarItem(
                        selected = currentRoute == "addFood",
                        onClick = {
                            navController.navigate("addFood") {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text("➕", fontSize = 18.sp)
                        },
                        label = { Text("Add") }
                    )

                    // list navigation
                    NavigationBarItem(
                        selected = currentRoute == "details",
                        onClick = {
                            navController.navigate("details") {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text("📋", fontSize = 18.sp)
                        },
                        label = { Text("List") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "community",
                        onClick = { navController.navigate("community") },
                        icon = { Text("🌍") },
                        label = { Text("Food Bank") }
                    )


                    // summary navigation
                    NavigationBarItem(
                        selected = currentRoute == "summary",
                        onClick = {
                            navController.navigate("summary") {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text("📊", fontSize = 18.sp)
                        },
                        label = { Text("Summary") }
                    )
                }
            }
        }
    ) { innerPadding ->

//  defines all screens in the app and maps each route to a composable screen
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("home") { HomeScreen(navController, viewModel) }

            composable("addFood") { AddFoodScreen(navController, viewModel) }

            composable("details") { FoodDetailScreen(navController, viewModel) }

            composable("recipes/{foodName}") { backStackEntry ->
                val foodName = backStackEntry.arguments?.getString("foodName") ?: ""

                RecipeScreen(
                    navController = navController,
                    foodName = foodName,
                    viewModel = viewModel
                )
            }

            composable("recipeDetail/{mealId}") { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""

                RecipeDetailScreen(
                    navController = navController,
                    mealId = mealId,
                    viewModel = viewModel
                )
            }

            composable("summary") { SummaryScreen(navController, viewModel) }

            composable("foodDetail/{index}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("index")?.toIntOrNull()

                FoodDetail(
                    navController = navController,
                    viewModel = viewModel,
                    id = id
                )
            }

            composable("editFood/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                EditFoodScreen(
                    navController = navController,
                    viewModel = viewModel,
                    id = id
                )
            }

            composable("community") {
                CommunityScreen(viewModel)
            }


        }
    }
}
