package com.app.trailblazer.trails

import java.io.Serializable

// Trail data is saved as a trail object list (this represents a single item on the list)
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
