package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import pl.gawor.taycknerdesktopclient.TaycknerApplication
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Category
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.CategoryRepository
import pl.gawor.taycknerdesktopclient.repository.entity.CategoryEntity
import pl.gawor.taycknerdesktopclient.service.Service
import pl.gawor.taycknerdesktopclient.service.mapper.CategoryMapper
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class DayTrackerController : Initializable, ISubscriber<Category> {

    @FXML private lateinit var button_categoryDelete: Button

    @FXML private lateinit var button_categoyAdd: Button

    @FXML private lateinit var gridPane_category: GridPane

    @FXML private lateinit var textArea_categoryDescription: TextArea

    @FXML private lateinit var textField_categoryColor: TextField

    @FXML private lateinit var textField_categoryName: TextField

    private var categories = ArrayList<Category>()

    private lateinit var categoryService: Service<Category, CategoryEntity>

    private var selectedItemCategory: Category? = null

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        val categoryRepository = CategoryRepository()
        val categoryMapper = CategoryMapper()
        categoryService = Service(categoryRepository, categoryMapper)

        refreshCategoriesList()
    }

    private fun refreshCategoriesList() {
        categories = categoryService.list() as ArrayList<Category>

        gridPane_category.children.clear()

        for ((row, model) in categories.withIndex()) {
            val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/day_tracker/item_category.fxml"))
            val vbox: VBox = fxmlLoader.load()

            val itemController = fxmlLoader.getController<ItemCategoryController>()
            itemController.set(model)
            itemController.subscribe(this)

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

    override fun update(model: Category) {
        selectedItemCategory = model
        refreshSelectedCategory()
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


}