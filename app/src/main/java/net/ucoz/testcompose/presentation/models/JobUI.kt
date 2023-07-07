package net.ucoz.testcompose.presentation.models

import net.ucoz.testcompose.data.models.Job

data class JobUI(
    var customerName: String,
    var activity: String,
    var grid: String,
    var fileName: String,
    var totalTime: String,
    var isPause: Boolean,
    var isCompleted: Boolean,
    var pauseName: String
)

fun JobUI(item: Job): JobUI {
    return JobUI(
        customerName = item.customerName,
        activity = item.activity,
        grid = item.grid,
        fileName = item.fileName,
        totalTime = item.totalTime,
        isPause = item.isPause,
        isCompleted = item.isCompleted,
        pauseName = item.pauseName,
    )
}
