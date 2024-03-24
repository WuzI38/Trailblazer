package com.lab.trailblazer.json

import java.io.Serializable

data class Trail(
    val trailName: String,
    val location: String,
    val description: String,
    val stages: List<String>
) : Serializable
