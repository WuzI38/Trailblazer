package com.app.trailblazer.trails

import java.io.Serializable

data class Trail(
    val trailName: String,
    val location: String,
    val difficulty: String,
    val description: String,
    val stages: List<String>
) : Serializable {
    val imageName: String
        get() = trailName.replace(" ", "_").replace("-", "_").replace("'", "").lowercase()
}
