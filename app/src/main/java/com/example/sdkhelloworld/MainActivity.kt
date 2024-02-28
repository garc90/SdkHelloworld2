package com.example.sdkhelloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sdkhelloworld.ui.theme.SdkHelloworldTheme
import com.evergage.android.ClientConfiguration
import com.evergage.android.Evergage
import com.evergage.android.LogLevel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Evergage.initialize(application)
        val evergage = Evergage.getInstance()
        Evergage.setLogLevel(LogLevel.ALL)

        evergage.start(
            ClientConfiguration.Builder()
                .account("agarcel523013338")
                .dataset("engage")
                .usePushNotifications(false)
                .build()
        )

        setContent {
            SdkHelloworldTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SdkHelloworldTheme {
        Greeting("Android")
    }
}