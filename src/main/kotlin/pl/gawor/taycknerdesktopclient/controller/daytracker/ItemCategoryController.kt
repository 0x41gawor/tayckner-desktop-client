package pl.gawor.taycknerdesktopclient.controller.daytracker

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import pl.gawor.taycknerdesktopclient.controller.Observer.ISubscriber
import pl.gawor.taycknerdesktopclient.model.Category

class ItemCategoryController{

    @FXML private lateinit var label_name: Label

    @FXML private lateinit var label_description: Label

    @FXML private lateinit var vbox_root: VBox

    private lateinit var model: Category

    fun set(model: Category) {
        this.model = model
        label_name.text = model.name
        label_description.text = model.description
        vbox_root.style = "-fx-background-color: ${model.color};"
    }

    @FXML fun vbox_rootOnMouseClicked() {
        notifySubscribers()
    }

    //---// S U B S C R I B E R S
    private val subscribers: ArrayList<ISubscriber<Category>> = ArrayList()

    //---// S U B S C R I B E
    fun subscribe(s: ISubscriber<Category>) {
        subscribers.add(s)
    }

    //---// U N S U B S C R I B E
    fun unsubscribe(s: ISubscriber<Category>) {
        subscribers.remove(s)
    }

    //---// N O T I F Y  S U B S C R I B E R S
    fun notifySubscribers() {
        for (s in subscribers) {
            s.update(model)
        }
    }
}