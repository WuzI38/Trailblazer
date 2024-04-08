package com.lab.trailblazer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lab.trailblazer.activities.timer.TimerActivity
import com.lab.trailblazer.json.JsonParser
import com.lab.trailblazer.ui.theme.TrailblazerTheme
import com.lab.trailblazer.ui.theme.activities.TrailList
import java.io.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val trailNames = JsonParser.trailListFromJson(this)
        setContent {
            TrailblazerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrailList(trailNames, { trailName ->
                        val trail = JsonParser.getTrailByName(this, trailName)
                        val intent = Intent(this, TrailActivity::class.java)
                        intent.putExtra("TRAIL", trail as Serializable)
                        startActivity(intent)
                    }, {
                        val intent = Intent(this, TimerActivity::class.java)
                        startActivity(intent)
                    })
                }
            }
        }
    }
}


