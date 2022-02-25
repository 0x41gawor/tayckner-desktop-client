package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import pl.gawor.taycknerdesktopclient.TaycknerApplication
import pl.gawor.taycknerdesktopclient.controller.util.DateDir
import pl.gawor.taycknerdesktopclient.model.Schedule
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.ScheduleRepository
import pl.gawor.taycknerdesktopclient.service.ScheduleService
import pl.gawor.taycknerdesktopclient.service.mapper.ScheduleMapper
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class Controller : Initializable {

    @FXML
    private lateinit var button_next: Button

    @FXML
    private lateinit var label_date: Label

    @FXML
    private lateinit var button_prev: Button

    @FXML
    private lateinit var gridPane: GridPane

    @FXML
    private lateinit var textField_name: TextField

    @FXML
    private lateinit var textField_duration: TextField

    @FXML
    private lateinit var textField_endTime: TextField

    @FXML
    private lateinit var textField_startTime: TextField

    @FXML
    private lateinit var button_add: Button

    @FXML
    private lateinit var button_delete: Button

    private var selectedDate = LocalDate.now()

    private var models = ArrayList<Schedule>()

    private lateinit var service: ScheduleService

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        val repository = ScheduleRepository()
        val mapper = ScheduleMapper()
        service = ScheduleService(repository, mapper)

        setSelectedDate(DateDir.TODAY)
        refreshList()
    }

    @FXML
    private fun button_nextOnMouseClicked() {
        setSelectedDate(DateDir.NEXT)
        refreshList()
    }

    @FXML
    private fun button_prevOnMouseClicked() {
        setSelectedDate(DateDir.PREV)
        refreshList()
    }

    @FXML
    private fun button_addOnMouseClicked() {
        val model = modelFromInput(textField_name, textField_startTime, textField_endTime, textField_duration)
        service.create(model)
        refreshList()
    }

    private fun refreshList() {
        models = service.list(selectedDate) as ArrayList<Schedule>

        gridPane.children.clear()

        for ((row, model) in models.withIndex()) {
            val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/item_schedule.fxml"))
            val hbox: HBox = fxmlLoader.load()

            val itemController = fxmlLoader.getController<ItemScheduleController>()
            itemController.set(model)
            gridPane.add(hbox, 1, row)
            gridPane.minWidth = Region.USE_COMPUTED_SIZE
            gridPane.minHeight = Region.USE_COMPUTED_SIZE
            gridPane.maxWidth = Region.USE_COMPUTED_SIZE
            gridPane.maxHeight = Region.USE_COMPUTED_SIZE
            gridPane.prefWidth = Region.USE_COMPUTED_SIZE
            gridPane.prefHeight = Region.USE_COMPUTED_SIZE
            GridPane.setMargin(hbox, Insets(7.0))
        }
    }

    private fun setSelectedDate(sel: DateDir) {
        selectedDate = when (sel) {
            DateDir.TODAY -> LocalDate.now()
            DateDir.PREV -> selectedDate.minusDays(1)
            DateDir.NEXT -> selectedDate.plusDays(1)
        }
        val day = selectedDate.dayOfMonth
        val month = selectedDate.month.name
        val year = selectedDate.year
        label_date.text = "$day ${month.substring(0, 3)} $year"
    }

    private fun modelFromInput(
        textfieldName: TextField,
        textfieldStartTime: TextField,
        textfieldEndTime: TextField,
        textfieldDuration: TextField
    ): Schedule {
        val name = textfieldName.text
        val startTime = timeTextToTime(textfieldStartTime.text)
        val endTime = timeTextToTime(textfieldEndTime.text)
        val duration = if (textfieldDuration.text == "") null else textfieldDuration.text.toDouble()
        return Schedule(0, name, startTime, endTime,duration, User())
    }

    private fun timeTextToTime(input: String): LocalDateTime? {
        println("Controller.timeTextToTime(input = $input)")
        if (input == "") return null
        val regex1 = Regex("\\d\\d[:]\\d\\d")
        if (!(regex1.matches(input))) return null
        val hour = input.substring(0, 2)
        val minute = input.substring(3, 5)
        println(hour)
        println(minute)
        val hourInt = hour.toInt()
        val minuteInt = minute.toInt()
        val day = selectedDate
        val result = LocalDateTime.of(day.year, day.month.value, day.dayOfMonth, hourInt, minuteInt)
        println("Controller.timeTextToTime(input = $input) = $result")
        return result
    }
}