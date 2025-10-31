package com.example.signupassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        CurrencyConverterScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen() {
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("EUR") }
    var convertedAmount by remember { mutableStateOf("0.00") }
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    val currencies = listOf("USD", "EUR", "GBP", "JPY", "INR", "CAD", "AUD", "CHF")
    
    // Simple exchange rates (relative to USD)
    val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "GBP" to 0.79,
        "JPY" to 149.50,
        "INR" to 83.12,
        "CAD" to 1.36,
        "AUD" to 1.54,
        "CHF" to 0.90
    )

    fun convertCurrency() {
        val inputAmount = amount.toDoubleOrNull() ?: 0.0
        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0
        val result = inputAmount * (toRate / fromRate)
        convertedAmount = "%.2f".format(result)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF131717),
                        Color(0xFF101212)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = "Currency Converter",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Amount Input
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // From Currency Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded1,
                onExpandedChange = { expanded1 = it }
            ) {
                OutlinedTextField(
                    value = fromCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("From") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded1,
                    onDismissRequest = { expanded1 = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                fromCurrency = currency
                                expanded1 = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // To Currency Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange = { expanded2 = it }
            ) {
                OutlinedTextField(
                    value = toCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("To") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false }
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                toCurrency = currency
                                expanded2 = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Convert Button
            Button(
                onClick = { convertCurrency() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "CONVERT",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6366F1)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Result Display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Converted Amount",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$convertedAmount $toCurrency",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
