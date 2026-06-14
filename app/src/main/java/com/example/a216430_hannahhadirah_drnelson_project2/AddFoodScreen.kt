package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun AddFoodScreen(navController: NavController, viewModel: FoodViewModel) {

    var name by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            )
            .verticalScroll(rememberScrollState())
    ) {

        // header
        AppHeader(
            title = "Add Food",
            subtitle = "Track expiry dates",
            showProfile = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        // input card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(modifier = Modifier.padding(12.dp)) {

                Text(
                    "Food Details",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Food Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text("Expiry Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // save button
                Button(
                    onClick = {
                        try {
                            val date = LocalDate.parse(expiryDate)

                            viewModel.addFood(name, date.toString())
                            navController.navigate("home")

                        } catch (e: Exception) {
                            errorMessage = "Use format: YYYY-MM-DD"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(6.dp)
                ) {
                    Text(
                        "Save Food",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // back button
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}