package com.lab.trailblazer.activities

import android.os.Bundle
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.lab.trailblazer.json.Trail

class TrailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = intent.extras

        // Use correct version of the function depending of the SDK version
        val trail: Trail? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.getSerializable("TRAIL", Trail::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle?.getSerializable("TRAIL") as? Trail
        }

        trail?.let {
            setContent {
                Text("Trail name: ${it.trailName}\n\n" +
                        "Location: ${it.location}\n\n" +
                        "Description: ${it.description}\n\n" +
                        "Stages:\n${it.stages.joinToString("\n")}")
            }
        }
    }
}
