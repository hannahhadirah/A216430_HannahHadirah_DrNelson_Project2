package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.a216430_hannahhadirah_drnelson_project2.data.local.FoodEntity


@Composable
fun FoodDetailScreen(navController: NavController, viewModel: FoodViewModel) {

    val foodList by viewModel.foodList.collectAsState()

//    delete function
    var foodToDelete by remember { mutableStateOf<FoodEntity?>(null) }


//    confirmation message to delete
    if (foodToDelete != null) {
        AlertDialog(
            onDismissRequest = { foodToDelete = null },
            title = { Text("Delete Food") },
            text = { Text("Are you sure you want to delete ${foodToDelete?.name}?") },
            confirmButton = {
                Button(
                    onClick = {
                        foodToDelete?.let { viewModel.deleteFood(it) }
                        foodToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { foodToDelete = null }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            )
            .padding(horizontal = 16.dp)

    ) {

        // header
        AppHeader(
            title = "Food List",
            subtitle = "Track all your stored food",
            showProfile = false
        )

        Spacer(modifier = Modifier.height(12.dp))

        // list food

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

// empty state
            if (foodList.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "📦",
                                fontSize = 42.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                "No Food Added Yet",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Start adding food items to track expiry dates",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            } else {

                // normal list

                items(foodList.withIndex().toList()) { item ->

                    val index = item.index
                    val food = item.value

                    val daysLeft = viewModel.getDaysLeft(food.expiryDate)

                    val statusColor = when {
                        daysLeft <= 1 -> MaterialTheme.colorScheme.error
                        daysLeft <= 2 -> Color(0xFFFF9800)
                        else -> Color(0xFF4CAF50)
                    }

                    val statusText = when {
                        daysLeft <= 1 -> "URGENT"
                        daysLeft <= 2 -> "SOON"
                        else -> "FRESH"
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("foodDetail/${food.id}")
                            },
                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(food.name, fontWeight = FontWeight.Bold)
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(statusColor.copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(statusText, color = statusColor)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            //edit button
                            IconButton(
                                onClick = {
                                    navController.navigate("editFood/${food.id}")
                                }
                            ) {
                                Text("✏️")
                            }

                            // delete button
                            IconButton(
                                onClick = {
                                    foodToDelete = food   // triggers confirmation dialog
                                }
                            ) {
                                Text("🗑️")
                            }

                        }
                    }
                }
            }
        }
    }
}