package com.example.privat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.privat.ui.theme.PrivatTheme


import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateInput = findViewById<EditText>(R.id.dateInput)
        val getRatesButton = findViewById<Button>(R.id.getRatesButton)
        val ratesTextView = findViewById<TextView>(R.id.ratesTextView)

        getRatesButton.setOnClickListener {
            val date = dateInput.text.toString()
            if (date.isNotEmpty() && isValidDate(date)) {
                fetchExchangeRates(date, ratesTextView)
            } else {
                Toast.makeText(this, "Введите корректную дату (формат: дд.мм.гггг)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchExchangeRates(date: String, ratesTextView: TextView) {
        val api = RetrofitClient.instance
        api.getExchangeRates(date).enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(
                call: Call<ExchangeRateResponse>,
                response: Response<ExchangeRateResponse>
            ) {
                if (response.isSuccessful) {
                    val rates = response.body()?.exchangeRate ?: emptyList()
                    val ratesText = rates.joinToString("\n") {
                        "${it.currency}: Покупка - ${it.purchaseRateNB}, Продажа - ${it.saleRateNB}"
                    }
                    ratesTextView.text = ratesText
                } else {
                    ratesTextView.text = "Ошибка загрузки данных"
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                ratesTextView.text = "Ошибка: ${t.message}"
            }
        })
    }

    private fun isValidDate(date: String): Boolean {
        return Regex("""\d{2}\.\d{2}\.\d{4}""").matches(date)
    }
}
