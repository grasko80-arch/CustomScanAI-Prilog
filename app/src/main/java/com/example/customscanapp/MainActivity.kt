package com.example.customscanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.customscanapp.ui.theme.CustomScanAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomScanAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CheckScreen()
                }
            }
        }
    }
}

@Composable
fun CheckScreen() {
    // Список стран
    val countries = listOf("France", "Germany", "Poland", "Luxembourg", "Ukraine")
    val borderTypes = listOf("Пешком", "Авто", "ЖД", "Авиа", "Корабль")

    var fromCountry by remember { mutableStateOf(countries[0]) }
    var toCountry by remember { mutableStateOf(countries[1]) }
    var borderType by remember { mutableStateOf(borderTypes[0]) }
    var itemKeyword by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    // Для выпадающих меню
    var expandedFrom by remember { mutableStateOf(false) }
    var expandedTo by remember { mutableStateOf(false) }
    var expandedBorder by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Выпадающий список "из какой страны"
        ExposedDropdownMenuBox(
            expanded = expandedFrom,
            onExpandedChange = { expandedFrom = !expandedFrom }
        ) {
            OutlinedTextField(
                value = fromCountry,
                onValueChange = {},
                label = { Text("Из какой страны") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expandedFrom,
                onDismissRequest = { expandedFrom = false }
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country) },
                        onClick = {
                            fromCountry = country
                            expandedFrom = false
                        }
                    )
                }
            }
        }

        // Выпадающий список "в какую страну"
        ExposedDropdownMenuBox(
            expanded = expandedTo,
            onExpandedChange = { expandedTo = !expandedTo }
        ) {
            OutlinedTextField(
                value = toCountry,
                onValueChange = {},
                label = { Text("В какую страну") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expandedTo,
                onDismissRequest = { expandedTo = false }
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country) },
                        onClick = {
                            toCountry = country
                            expandedTo = false
                        }
                    )
                }
            }
        }

        // Выпадающий список "способ пересечения"
        ExposedDropdownMenuBox(
            expanded = expandedBorder,
            onExpandedChange = { expandedBorder = !expandedBorder }
        ) {
            OutlinedTextField(
                value = borderType,
                onValueChange = {},
                label = { Text("Способ пересечения") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expandedBorder,
                onDismissRequest = { expandedBorder = false }
            ) {
                borderTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            borderType = type
                            expandedBorder = false
                        }
                    )
                }
            }
        }

        // Поле для предмета (пока текстовое)
        OutlinedTextField(
            value = itemKeyword,
            onValueChange = { itemKeyword = it },
            label = { Text("Предмет для проверки") },
            modifier = Modifier.fillMaxWidth()
        )

        // Кнопка "Проверить"
        Button(
            onClick = {
                val request = CheckRequest(
                    fromCountry,
                    toCountry,
                    borderType,
                    itemKeyword
                )
                RetrofitClient.instance.checkItem(request)
                    .enqueue(object : Callback<CheckResponse> {
                        override fun onResponse(
                            call: Call<CheckResponse>,
                            response: Response<CheckResponse>
                        ) {
                            if (response.isSuccessful) {
                                resultText = response.body()?.result ?: "Нет ответа"
                            } else {
                                resultText = "Ошибка: ${response.code()}"
                            }
                        }

                        override fun onFailure(call: Call<CheckResponse>, t: Throwable) {
                            resultText = "Ошибка сети: ${t.message}"
                        }
                    })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить")
        }

        // Отображение результата
        Text(
            text = resultText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
