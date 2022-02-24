package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import pl.gawor.taycknerdesktopclient.model.Schedule
import java.time.LocalDateTime
import kotlin.math.floor

class ItemScheduleController {
    @FXML private lateinit var label_duration: Label

    @FXML private lateinit var label_name: Label

    @FXML private lateinit var label_time: Label

    private lateinit var model: Schedule

    fun set(model: Schedule) {
        this.model = model
        label_name.text = model.name
        label_time.text = getTimeText(model.startTime, model.endTime)
        label_duration.text = getDurationText(model.duration)
    }

    private fun getTimeText(start: LocalDateTime, end: LocalDateTime): String {
        val startTime =start.hour.toString() + ":" + model.startTime.minute
        val endTime = end.hour.toString() + ":" + model.endTime.minute
        return "$startTime - $endTime"
    }

    private fun getDurationText(duration: Double): String {
        var result = if (duration == floor(duration)) duration.toInt().toString() else duration.toString()
        result += "h"
        return result
    }

}