package pl.gawor.taycknerdesktopclient.controller.habittracker

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.HabitEvent

class ItemHabitEventController {

    @FXML private lateinit var hbox_root: HBox

    @FXML private lateinit var label_comment: Label

    @FXML private lateinit var label_count: Label

    @FXML private lateinit var label_day: Label

    @FXML private lateinit var label_habit: Label

    @FXML private lateinit var label_month: Label

    @FXML private lateinit var label_year: Label

    private lateinit var model: HabitEvent

    fun set(model: HabitEvent) {
        this.model = model
        label_habit.text = model.habit.name
        label_day.text = model.date.dayOfMonth.toString()
        label_month.text = model.date.month.name.substring(0,3)
        label_year.text = model.date.year.toString()
        label_comment.text = model.comment
        label_count.text = if (model.count != 1) model.count.toString() else ""
        hbox_root.style = "-fx-background-color: ${model.habit.color};"
    }

    @FXML  fun hbox_rootOnMouseClicked() {
        notifySubscribers()
    }
    //---// S U B S C R I B E R S
    private val subscribers: ArrayList<ISubscriber<HabitEvent>> = ArrayList()

    //---// S U B S C R I B E
    fun subscribe(s: ISubscriber<HabitEvent>) {
        subscribers.add(s)
    }

    //---// U N S U B S C R I B E
    fun unsubscribe(s: ISubscriber<HabitEvent>) {
        subscribers.remove(s)
    }

    //---// N O T I F Y  S U B S C R I B E R S
    private fun notifySubscribers() {
        for (s in subscribers) {
            s.update(model)
        }
    }
}