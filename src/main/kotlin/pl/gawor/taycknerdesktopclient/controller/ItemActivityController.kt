package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Activity
import java.time.LocalTime

class ItemActivityController {
    @FXML private lateinit var label_breaks: Label

    @FXML private lateinit var label_category: Label

    @FXML private lateinit var label_name: Label

    @FXML private lateinit var label_time: Label

    @FXML private lateinit var hbox_root: HBox

    private lateinit var model: Activity

    fun set(model: Activity) {
        this.model = model
        label_category.text = model.category.name
        label_name.text = model.name
        label_time.text = getTimeText(model.startTime, model.endTime)
        label_breaks.text = if (model.breaks == 0) "" else model.breaks.toString()
        hbox_root.style = "-fx-background-color: ${model.category.color};"
    }

    @FXML fun hboxOnMouseClicked() {
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

    //---// S U B S C R I B E R S
    private val subscribers: ArrayList<ISubscriber<Activity>> = ArrayList()

    //---// S U B S C R I B E
    fun subscribe(s: ISubscriber<Activity>) {
        subscribers.add(s)
    }

    //---// U N S U B S C R I B E
    fun unsubscribe(s: ISubscriber<Activity>) {
        subscribers.remove(s)
    }

    //---// N O T I F Y  S U B S C R I B E R S
    private fun notifySubscribers() {
        for (s in subscribers) {
            s.update(model)
        }
    }
}