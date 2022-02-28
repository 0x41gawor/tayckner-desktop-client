package pl.gawor.taycknerdesktopclient.controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import pl.gawor.taycknerdesktopclient.controller.Observer.IPublisher
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Category

class ItemCategoryController : IPublisher<Category>{

    @FXML private lateinit var label_name: Label

    @FXML private lateinit var label_description: Label

    @FXML private lateinit var vbox_root: VBox

    private lateinit var model: Category

    fun set(model: Category) {
        this.model = model
        label_name.text = model.name
        label_description.text = model.description
        vbox_root.style.plus("-fx-background-color: ${model.color}")
    }

    @FXML fun vbox_rootOnMouseClicked() {
        notifySubscribers()
    }

    override val subscribers: ArrayList<ISubscriber<Category>>
        get() = ArrayList()

    override fun notifySubscribers() {
        for (s in subscribers) {
            s.update(model)
        }
    }
}