package net.ucoz.testcompose.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Job(
    val customerName: String,
    var activity: String,
    var grid: String,
    var fileName: String,
    var totalTime: String,
    var isPause: Boolean,
    var isCompleted: Boolean,
    var pauseName: String,
)
