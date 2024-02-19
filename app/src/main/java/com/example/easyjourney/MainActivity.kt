package com.example.easyjourney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.easyjourney.ui.theme.EasyJourneyTheme



data class Place(val name: String, val distance: Number)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyJourneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreenContent()
                }
            }
        }
    }
}




@Composable
fun MyScreenContent() {
    var highlightedIndex by remember { mutableIntStateOf(0) }
    var isDistanceInKm by remember { mutableStateOf(true) }
    var totalDistanceCovered by remember { mutableDoubleStateOf(0.0) }
    var vadodaraReached by remember { mutableStateOf(false) }



    val differentStops = listOf(
        Place("Delhi", 0),
        Place("Noida", 100),
        Place("Mathura", 200),
        Place("Agra", 300),
        Place("Kanpur", 400),
        Place("Lucknow", 500),
        Place("Varanasi", 600),
        Place("Allahabad", 700),
        Place("Patna", 800),
        Place("Ranchi", 900),
        Place("Kolkata", 1000),
        Place("Mumbai", 1100),
        Place("Pune", 1200),
        Place("Jaipur", 1300),
        Place("Ajmer", 1400),
        Place("Jodhpur", 1500),
        Place("Udaipur", 1600),
        Place("Ahmedabad", 1700),
        Place("Surat", 1800),
        Place("Vadodara", 1900)
    )



    val totalStops = differentStops.size

    Column(modifier = Modifier.fillMaxSize()) {

        LinearProgressIndicator(
            progress = (highlightedIndex + 1) / totalStops.toFloat(),
            color = Color.Red,
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Stations", modifier = Modifier.padding(16.dp), style = TextStyle(fontStyle = FontStyle.Italic))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Distance of highlighted station from previous station", modifier = Modifier.padding(10.dp), style = TextStyle(fontStyle = FontStyle.Italic))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.height(300.dp)) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(differentStops) { index, item ->
                    val distance = if (isDistanceInKm) item.distance else item.distance.toDouble()/1.609
                    ItemRow(item = item.copy(distance = distance), highlighted = index == highlightedIndex, isDistanceInKm)
                }
            }
        }




        Spacer(modifier = Modifier.height(35.dp))

        Box{
            Column (modifier = Modifier.padding(15.dp)){
                Text(text = "Current station = ${differentStops[highlightedIndex].name}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Distance between the current station and previous station = ${if (isDistanceInKm) differentStops[highlightedIndex].distance else differentStops[highlightedIndex].distance.toDouble()/1.609} ${if (isDistanceInKm) "km" else "miles"}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Distance covered till now= ${if (isDistanceInKm) totalDistanceCovered else totalDistanceCovered.toDouble()/1.609} ${if (isDistanceInKm) "km" else "miles"}",)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Total Distance left to reach Vadodara= ${if (isDistanceInKm) (19000-totalDistanceCovered.toDouble()) else (19000-totalDistanceCovered.toDouble())/1.609} ${if (isDistanceInKm) "km" else "miles"}"
                )
            }

        }

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    isDistanceInKm = !isDistanceInKm
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Change Unit (Km / Mile)")
            }
            Button(
                onClick = { highlightedIndex = (highlightedIndex + 1) % differentStops.size
                    totalDistanceCovered += differentStops[highlightedIndex].distance.toDouble()
                    if (highlightedIndex == differentStops.indexOfFirst { it.name == "Vadodara" }) {
                        vadodaraReached = true}
                },
                enabled = !vadodaraReached
            ) {
                Text(text = "Move to next stop")
            }
        }
    }
}





@Composable
fun MyScreenContentSecond() {
    var highlightedIndex by remember { mutableIntStateOf(0) }
    var isDistanceInKm by remember { mutableStateOf(true) }
    var totalDistanceCovered by remember { mutableDoubleStateOf(0.0) }
    var vadodaraReached by remember { mutableStateOf(false) }



    val differentStops = listOf(
        Place("Delhi", 0),
        Place("Noida", 100),
        Place("Mathura", 200),
        Place("Udaipur", 1600),
        Place("Ahmedabad", 1700),
        Place("Surat", 1800),
        Place("Vadodara", 1900)
    )




    val totalStops = differentStops.size

    Column(modifier = Modifier.fillMaxSize()) {

        LinearProgressIndicator(
            progress = (highlightedIndex + 1) / totalStops.toFloat(),
            color = Color.Red,
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Stations", modifier = Modifier.padding(16.dp), style = TextStyle(fontStyle = FontStyle.Italic))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Distance of highlighted station from previous station", modifier = Modifier.padding(10.dp), style = TextStyle(fontStyle = FontStyle.Italic))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            differentStops.forEachIndexed { index, item ->
                val distance = if (isDistanceInKm) item.distance else item.distance.toDouble()/1.609
                ItemRow(
                    item = item.copy(distance = distance),
                    highlighted = index == highlightedIndex,
                    isDistanceInKm
                )
            }
        }




        Spacer(modifier = Modifier.height(35.dp))

        Box{
            Column (modifier = Modifier.padding(15.dp)){
                Text(text = "Current station = ${differentStops[highlightedIndex].name}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Distance between the current station and previous station = ${if (isDistanceInKm) differentStops[highlightedIndex].distance else differentStops[highlightedIndex].distance.toDouble()/1.609} ${if (isDistanceInKm) "km" else "miles"}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Distance covered till now= ${if (isDistanceInKm) totalDistanceCovered else totalDistanceCovered.toDouble()/1.609} ${if (isDistanceInKm) "km" else "miles"}",)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Total Distance left to reach Vadodara= ${if (isDistanceInKm) (7300-totalDistanceCovered.toDouble()) else (7300-totalDistanceCovered.toDouble())/1.609} ${if (isDistanceInKm) "km" else "miles"}"
                )
            }

        }

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    isDistanceInKm = !isDistanceInKm
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Change Unit (Km / Mile)")
            }
            Button(
                onClick = { highlightedIndex = (highlightedIndex + 1) % differentStops.size
                    totalDistanceCovered += differentStops[highlightedIndex].distance.toDouble()
                    if (highlightedIndex == differentStops.indexOfFirst { it.name == "Vadodara" }) {
                        vadodaraReached = true}
                },
                enabled = !vadodaraReached
            ) {
                Text(text = "Move to next stop")
            }
        }
    }
}



@Composable
fun ItemRow(item: Place, highlighted: Boolean, isDistanceInKm: Boolean) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = if (highlighted) Color.Yellow else Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "${item.distance} ${if (isDistanceInKm) "km" else "miles"}",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreviewSecond() {
    EasyJourneyTheme {
        MyScreenContent()
    }
}