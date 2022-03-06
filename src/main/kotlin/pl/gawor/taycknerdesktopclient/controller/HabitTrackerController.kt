package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import pl.gawor.taycknerdesktopclient.TaycknerApplication
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Category
import pl.gawor.taycknerdesktopclient.model.Habit
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.HabitRepository
import pl.gawor.taycknerdesktopclient.repository.entity.HabitEntity
import pl.gawor.taycknerdesktopclient.service.Service
import pl.gawor.taycknerdesktopclient.service.mapper.HabitMapper
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class HabitTrackerController : Initializable {

    // H A B I T

    @FXML private lateinit var gridPane_habit: GridPane

    @FXML private lateinit var button_habitAdd: Button

    @FXML private lateinit var button_habitDelete: Button

    @FXML private lateinit var textField_habitName: TextField

    @FXML private lateinit var colorPicker: ColorPicker

    @FXML private lateinit var textArea_habitDescription: TextArea

    // H A B I T - E V E N T

    @FXML private lateinit var gridPane_habitEvent: GridPane

    @FXML private lateinit var button_habitEventAdd: Button

    @FXML private lateinit var button_habitEventDelete: Button

    @FXML private lateinit var textField_habitEventName: TextField

    @FXML private lateinit var comboBox_habitEventHabit: ComboBox<Any>

    @FXML private lateinit var datePicker: DatePicker

    @FXML private lateinit var textField_habitEventCount: TextField

    // P R I V A T E

    private var habits = ArrayList<Habit>()

    private lateinit var habitService: Service<Habit, HabitEntity>

    private var selectedItemHabit: Habit? = null

    private val habitListener = object : ISubscriber<Habit> {
        override fun update(model: Habit) {
            selectedItemHabit = model
            refreshSelectedHabit()
        }
    }



    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        val habitRepository = HabitRepository()
        val habitMapper = HabitMapper()
        habitService = Service(habitRepository, habitMapper)

        refreshHabitsList()
    }

    private fun refreshHabitsList() {
        habits = habitService.list() as ArrayList<Habit>

        gridPane_habit.children.clear()

        for ((row, model) in habits.withIndex()) {
            val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/habit_tracker/item_habit.fxml"))
            val root: VBox = fxmlLoader.load()

            val itemController = fxmlLoader.getController<ItemHabitController>()
            itemController.set((model))
            itemController.subscribe(this.habitListener)

            gridPane_habit.add(root, 1, row)
            gridPane_habit.minWidth = Region.USE_COMPUTED_SIZE
            gridPane_habit.minHeight = Region.USE_COMPUTED_SIZE
            gridPane_habit.maxWidth = Region.USE_COMPUTED_SIZE
            gridPane_habit.maxHeight = Region.USE_COMPUTED_SIZE
            gridPane_habit.prefWidth = Region.USE_COMPUTED_SIZE
            gridPane_habit.prefHeight = Region.USE_COMPUTED_SIZE
            GridPane.setMargin(root, Insets(5.0))
        }
    }

    fun button_habitAddOnAction() {
        val model = Habit(0, textField_habitName.text, textArea_habitDescription.text, "#" + Integer.toHexString(colorPicker.value.hashCode()).substring(0,6), User())
        habitService.create(model)
        refreshHabitsList()
    }

    fun button_habitDeleteOnAction() {
        if (selectedItemHabit != null) {
            habitService.delete(selectedItemHabit!!.id)
            selectedItemHabit = null
            refreshSelectedHabit()
            refreshHabitsList()
        }
    }

    fun textField_habitNameOnAction() {
        if (selectedItemHabit != null) {
            val model = Habit(selectedItemHabit!!.id, textField_habitName.text, textArea_habitDescription.text, "#" + Integer.toHexString(colorPicker.value.hashCode()).substring(0,6), User())
            habitService.update(model.id, model)
            refreshHabitsList()
        }
    }

    private fun refreshSelectedHabit() {
        if (selectedItemHabit == null) {
            textField_habitName.text = ""
            colorPicker.value = Color.WHITE
            textArea_habitDescription.text = ""
        } else {
            textField_habitName.text = selectedItemHabit!!.name
            colorPicker.value = Color.valueOf(selectedItemHabit!!.color)
            textArea_habitDescription.text = selectedItemHabit!!.description
        }
    }


}