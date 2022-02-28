package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import pl.gawor.taycknerdesktopclient.TaycknerApplication
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Activity
import pl.gawor.taycknerdesktopclient.model.Category
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.ActivityRepository
import pl.gawor.taycknerdesktopclient.repository.CategoryRepository
import pl.gawor.taycknerdesktopclient.repository.entity.CategoryEntity
import pl.gawor.taycknerdesktopclient.service.ActivityService
import pl.gawor.taycknerdesktopclient.service.Service
import pl.gawor.taycknerdesktopclient.service.mapper.ActivityMapper
import pl.gawor.taycknerdesktopclient.service.mapper.CategoryMapper
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class DayTrackerController : Initializable {

    // C A T E G O R Y

    @FXML private lateinit var button_categoryDelete: Button

    @FXML private lateinit var button_categoyAdd: Button

    @FXML private lateinit var gridPane_category: GridPane

    @FXML private lateinit var textArea_categoryDescription: TextArea

    @FXML private lateinit var textField_categoryColor: TextField

    @FXML private lateinit var textField_categoryName: TextField

    // A C T I V I T Y

    @FXML private lateinit var butoon_prev: Button

    @FXML private lateinit var button_activity_add: Button

    @FXML private lateinit var button_activity_delete: Button

    @FXML private lateinit var button_next: Button

    @FXML private lateinit var comboBox_activiy_category: ComboBox<Any>

    @FXML private lateinit var gridPane_activity: GridPane

    @FXML private lateinit var label_date: Label

    @FXML private lateinit var textField_activity_breaks: TextField

    @FXML private lateinit var textField_activity_endTime: TextField

    @FXML private lateinit var textField_activity_name: TextField

    @FXML private lateinit var textField_activity_startTime: TextField

    private var categories = ArrayList<Category>()
    private var activities = ArrayList<Activity>()

    private lateinit var categoryService: Service<Category, CategoryEntity>
    private lateinit var activityService: ActivityService

    private var selectedItemCategory: Category? = null
    private var selectedItemActivity: Activity? = null

    private val categoryListener = object : ISubscriber<Category> {
        override fun update(model: Category) {
            selectedItemCategory = model
            refreshSelectedCategory()
        }
    }

    private val activityListener = object : ISubscriber<Activity> {
        override fun update(model: Activity) {
            selectedItemActivity = model
            refreshSelectedActivity()
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        val categoryRepository = CategoryRepository()
        val categoryMapper = CategoryMapper()
        categoryService = Service(categoryRepository, categoryMapper)
        val activityRepository = ActivityRepository()
        val activityMapper = ActivityMapper()
        activityService = ActivityService(activityRepository, activityMapper)

        refreshCategoriesList()
        refreshActivitiesList()
    }

    private fun refreshCategoriesList() {
        categories = categoryService.list() as ArrayList<Category>

        gridPane_category.children.clear()

        for ((row, model) in categories.withIndex()) {
            val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/day_tracker/item_category.fxml"))
            val vbox: VBox = fxmlLoader.load()

            val itemController = fxmlLoader.getController<ItemCategoryController>()
            itemController.set(model)
            itemController.subscribe(this.categoryListener)

            gridPane_category.add(vbox, 1, row)
            gridPane_category.minWidth = Region.USE_COMPUTED_SIZE
            gridPane_category.minHeight = Region.USE_COMPUTED_SIZE
            gridPane_category.maxWidth = Region.USE_COMPUTED_SIZE
            gridPane_category.maxHeight = Region.USE_COMPUTED_SIZE
            gridPane_category.prefWidth = Region.USE_COMPUTED_SIZE
            gridPane_category.prefHeight = Region.USE_COMPUTED_SIZE
            GridPane.setMargin(vbox, Insets(5.0))
        }
    }

    private fun refreshActivitiesList() {
        activities = activityService.list() as ArrayList<Activity>

        gridPane_activity.children.clear()

        for ((row, model) in activities.withIndex()) {
            val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/day_tracker/item_activity.fxml"))
            val root: HBox = fxmlLoader.load()

            val itemController = fxmlLoader.getController<ItemActivityController>()
            itemController.set(model)
            itemController.subscribe(this.activityListener)

            gridPane_activity.add(root, 1, row)
            gridPane_activity.minWidth = Region.USE_COMPUTED_SIZE
            gridPane_activity.minHeight = Region.USE_COMPUTED_SIZE
            gridPane_activity.maxWidth = Region.USE_COMPUTED_SIZE
            gridPane_activity.maxHeight = Region.USE_COMPUTED_SIZE
            gridPane_activity.prefWidth = Region.USE_COMPUTED_SIZE
            gridPane_activity.prefHeight = Region.USE_COMPUTED_SIZE
            GridPane.setMargin(root, Insets(10.0))
        }
    }

    private fun refreshSelectedCategory() {
        if (selectedItemCategory == null) {
            textField_categoryName.text = ""
            textField_categoryColor.text = ""
            textArea_categoryDescription.text = ""
        } else {
            textField_categoryName.text = selectedItemCategory!!.name
            textField_categoryColor.text = selectedItemCategory!!.color
            textArea_categoryDescription.text = selectedItemCategory!!.description
        }
    }

    private fun refreshSelectedActivity() {
        if (selectedItemActivity == null) {
            comboBox_activiy_category.promptText = ""
            textField_activity_name.text = ""
            textField_activity_startTime.text = ""
            textField_activity_endTime.text = ""
            textField_activity_breaks.text = ""
        } else {
            comboBox_activiy_category.promptText = selectedItemActivity!!.category.name
            textField_activity_name.text = selectedItemActivity!!.name
            textField_activity_startTime.text = selectedItemActivity!!.startTime.toString().substring(0, 5)
            textField_activity_endTime.text = if (selectedItemActivity!!.endTime == null) "" else selectedItemActivity!!.endTime.toString().substring(0, 5)
            textField_activity_breaks.text = if (selectedItemActivity!!.breaks == 0) "" else selectedItemActivity!!.breaks.toString()
        }
    }

    fun button_category_addOnAction() {
        val model = Category(0, textField_categoryName.text, textArea_categoryDescription.text, textField_categoryColor.text, User())
        categoryService.create(model)
        refreshCategoriesList()
    }

    fun button_category_deleteOnAction() {
        if (selectedItemCategory != null) {
            categoryService.delete(selectedItemCategory!!.id)
            selectedItemCategory = null
            refreshSelectedCategory()
            refreshCategoriesList()
        }
    }

    fun hbox_category_crud_inputsOnAction() {
        if (selectedItemCategory != null) {
            val model = Category(selectedItemCategory!!.id, textField_categoryName.text, textArea_categoryDescription.text, textField_categoryColor.text, User())
            categoryService.update(model.id, model)
            refreshCategoriesList()
        }
    }


}