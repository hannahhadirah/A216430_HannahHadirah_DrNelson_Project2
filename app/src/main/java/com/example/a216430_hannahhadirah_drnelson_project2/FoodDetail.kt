package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FoodDetail(
    navController: NavController,
    viewModel: FoodViewModel,
    id: Int?
) {

    val foodList by viewModel.foodList.collectAsState()
    val food = id?.let { foodList.find { item -> item.id == id } }

    var showDonateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            )
    ) {

        AppHeader(
            title = "Food Detail",
            subtitle = "Single item view",
            showProfile = false,
            showBack = true,
            onBackClick = { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (food == null) {
            Text("Food not found ❌")
            return
        }

        val daysLeft = viewModel.getDaysLeft(food.expiryDate)

        val status = when {
            daysLeft < 0 -> "EXPIRED"
            daysLeft <= 1 -> "URGENT"
            daysLeft <= 3 -> "SOON"
            else -> "FRESH"
        }

        val freshnessMessage = when {
            daysLeft < 0 ->
                "This item has expired. Consider discarding it."

            daysLeft <= 1 ->
                "Consume this food as soon as possible."

            daysLeft <= 3 ->
                "This item should be used soon."

            else ->
                "This food is still fresh."
        }

        // alert dialog
        if (showDonateDialog) {
            AlertDialog(
                onDismissRequest = { showDonateDialog = false },
                title = {
                    Text("Donation Successful 🎉")
                },
                text = {
                    Text("${food.name} has been donated to the Community Food Bank.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.donateFood(food.name, food.expiryDate)
                            viewModel.deleteFood(food)

                            showDonateDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = food.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("📅 Expiry: ${food.expiryDate}")
                    Text("⏳ Days Left: $daysLeft")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Status: $status",
                        color = when (status) {
                            "EXPIRED" -> MaterialTheme.colorScheme.error
                            "URGENT" -> Color(0xFFFF5722)
                            "SOON" -> Color(0xFFFF9800)
                            else -> Color(0xFF4CAF50)
                        },
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "📂 Category",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        getFoodCategory(food.name)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "💡 Freshness Note",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        freshnessMessage
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "🧊 Storage Tip",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        getStorageTip(food.name)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate(
                        "recipes/${food.name}"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🍽 Get Recipe")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    showDonateDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Donate 🍱")
            }

        }
    }