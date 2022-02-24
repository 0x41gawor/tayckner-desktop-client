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
import pl.gawor.taycknerdesktopclient.repository.ScheduleRepository
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity
import pl.gawor.taycknerdesktopclient.service.Service
import pl.gawor.taycknerdesktopclient.service.mapper.ScheduleMapper
import java.net.URL
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class Controller : Initializable {

    @FXML private lateinit var button_next: Button

    @FXML private lateinit var label_date: Label

    @FXML private lateinit var button_prev: Button

    @FXML private lateinit var gridPane: GridPane

    @FXML private lateinit var textField_name: TextField

    @FXML private lateinit var textField_duration: TextField

    @FXML private lateinit var textField_endTime: TextField

    @FXML private lateinit var textField_startTime: TextField

    @FXML private lateinit var button_add: Button

    @FXML private lateinit var button_delete: Button

    private var selectedDate = LocalDate.now()

    private var models = ArrayList<Schedule>()

    private lateinit var service: Service<Schedule, ScheduleEntity>


    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        val repository = ScheduleRepository()
        val mapper = ScheduleMapper()
        service = Service(repository, mapper)

        setSelectedDate(DateDir.TODAY)
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


}