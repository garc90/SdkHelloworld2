package com.example.sdkhelloworld

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.evergage.android.Campaign
import com.evergage.android.CampaignHandler
import com.evergage.android.ClientConfiguration
import com.evergage.android.Evergage
import com.evergage.android.Evergage.TAG
import com.evergage.android.Evergage.getInstance
import com.evergage.android.LogLevel
import com.evergage.android.Screen
import com.evergage.android.promote.Product
import com.example.sdkhelloworld.ui.theme.SdkHelloworldTheme
import coil.load

public interface Campaign


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //ViewState should be constructed from Campaign Data

        super.onCreate(savedInstanceState)
        Evergage.initialize(application)
        val evergage = Evergage.getInstance()
        Evergage.setLogLevel(LogLevel.ALL)

        val screen = getInstance().globalContext


        if (screen != null) {


            // If screen is viewing a product:
            //screen.viewItem(Product("p123"))

            // If screen is viewing a category, like women's merchandise:
            //screen.viewCategory(Category("Womens"))

            // Or if screen is viewing a tag, like some specific brand:
            //screen.viewTag(Tag("SomeBrand", Tag.Type.Brand))

            // Or maybe screen isn't related to your catalog:
            //screen.trackAction("Home")
        }




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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val screen2 = getInstance().globalContext
        //val trackingAction: String = "Home"
        val campaignTarget: String = "experienceTarget"
        var activeCampaign: Campaign? = null
        //  val handler: CampaignHandler? = null

        //ViewState should be constructed from Campaign Data


        screen2?.let {
            val handler = object : CampaignHandler {
                override fun handleCampaign(campaign: Campaign) {
                    val bannerType = campaign.data.optString("bannerType")
                    val model = campaign.data.optString("imageUrl")
                    val contentDesription = campaign.data.optString("body")

                    Log.d("TAG", "Campanha" + campaign.data)
                    if (bannerType.isEmpty()) {
                        // Log.d(TAG, "No bannerType")
                        return
                    }

                    if (activeCampaign != null && activeCampaign == campaign) {
                        // Log.d(TAG, "Ignoring campaign name " + campaign.campaignName + " since equivalent content is already active")
                        return
                    }

                    // Track the impression for statistics even if the user is in the control group.
                    screen2.trackImpression(campaign)

                    // Only display the campaign if the user is not in the control group.
                    if (!campaign.isControlGroup) {
                        activeCampaign = campaign
                        // Keep active campaign as long as needed for (re)display and comparison
                        //Log.d(TAG, "New active campaign name " + campaign.campaignName + " for target " + campaign.target + " with data " + campaign.data)

                        //Custom campaign tracking


                    }


                }
            }

            screen2.setCampaignHandler(handler, campaignTarget)
            screen2.trackAction("Home")



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

}