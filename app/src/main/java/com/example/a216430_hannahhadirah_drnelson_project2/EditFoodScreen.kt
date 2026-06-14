package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import com.example.a216430_hannahhadirah_drnelson_project2.data.local.FoodEntity

@Composable
fun EditFoodScreen(
    navController: NavController,
    viewModel: FoodViewModel,
    id: Int?
) {
    val foodList by viewModel.foodList.collectAsState()
    val food = id?.let { foodId -> foodList.find { it.id == foodId } }

    var editedName by remember { mutableStateOf(food?.name ?: "") }
    var editedDate by remember { mutableStateOf(food?.expiryDate ?: "") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            )
            .padding(16.dp)
    ) {

        AppHeader(
            title = "Edit Food",
            subtitle = "Update food details",
            showProfile = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (food == null) {
            Text("Food not found ❌")
            return
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Edit Details", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = editedName,
                    onValueChange = { editedName = it },
                    label = { Text("Food Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = editedDate,
                    onValueChange = { editedDate = it },
                    label = { Text("Expiry Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            try {
                                java.time.LocalDate.parse(editedDate.trim())
                                viewModel.updateFood(
                                    FoodEntity(
                                        id = food.id,
                                        name = editedName.trim(),
                                        expiryDate = editedDate.trim()
                                    )
                                )
                                navController.popBackStack()
                            } catch (e: Exception) {
                                errorMessage = "Use format: YYYY-MM-DD"
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}