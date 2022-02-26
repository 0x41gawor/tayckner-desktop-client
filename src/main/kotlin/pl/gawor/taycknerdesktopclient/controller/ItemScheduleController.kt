package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Schedule
import java.time.LocalTime
import kotlin.math.floor

//---// P U B L I S H E R
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

    @FXML private fun hboxOnMouseClicked() {
        notifySubscribers()
    }

    private fun getTimeText(start: LocalTime?, end: LocalTime?): String {
        fun localTimeToString(time: LocalTime?) : String = if (time == null) "" else {
            var hour = time.hour.toString()
            if (time.hour < 10) {
                hour = "0$hour"
            }
            var minute = time.minute.toString()
            if (time.minute < 10) {
                minute = "0$minute"
            }
            "$hour:$minute"
        }

        val startTime = localTimeToString(start)
        val endTime = localTimeToString(end)

        return "$startTime - $endTime"
    }

    private fun getDurationText(duration: Double?): String {
        if (duration == null) return ""
        var result = if (duration == floor(duration)) duration.toInt().toString() else duration.toString()
        result += "h"
        return result
    }

    //---// S U B S C R I B E R S
    private val subscribers: ArrayList<ISubscriber<Schedule>> = ArrayList()

    //---// S U B S C R I B E
    fun subscribe(s: ISubscriber<Schedule>) {
        subscribers.add(s)
    }

    //---// U N S U B S C R I B E
    fun unsubscribe(s: ISubscriber<Schedule>) {
        subscribers.remove(s)
    }

    //---// N O T I F Y  S U B S C R I B E R S
    private fun notifySubscribers() {
        for (s in subscribers) {
            s.update(model)
        }
    }

}