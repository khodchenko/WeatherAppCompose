package com.khodchenko.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.khodchenko.weatherappcompose.screens.MainCard
import com.khodchenko.weatherappcompose.screens.TabLayout
import com.khodchenko.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY = "c2ad9b8c2f2e4efbaa5190047233110"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                getData("London", this)
                Image(
                    painter = painterResource(id = R.drawable.weather_bg),
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column {
                    MainCard()
                    TabLayout()
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context, modifier: Modifier = Modifier) {
    val state = remember {
        mutableStateOf("Unknown")
    }
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Temp in $name = ${state.value}")
        }
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = {
                getResult(name, state, context)
            }, modifier = Modifier.padding(5.dp)) {
                Text(text = "Refresh")
            }
        }
    }

}

private fun getResult(city: String, state: MutableState<String>, context: Context) {
    val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$city&aqi=no"

    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("current").getString("temp_c") + " C"
        },
        { error -> Log.d("MyLog", "Error:$error") }
    )

    queue.add(stringRequest)
}

private fun getData(city: String, context: Context) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY" +
            "&q=$city" +
            "&days=" +
            "3" +
            "1&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            Log.d("MyLog", "Response :$response")
        }, {
            Log.d("MyLog", "Response error:$it")
        }
    )

    queue.add(stringRequest)
}