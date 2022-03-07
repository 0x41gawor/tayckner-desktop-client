package pl.gawor.taycknerdesktopclient

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import pl.gawor.taycknerdesktopclient.controller.Observer.INavigationSubscriber
import pl.gawor.taycknerdesktopclient.controller.daytracker.DayTrackerController

class TaycknerApplication : Application() {

    private val listener = object : INavigationSubscriber {
        override fun button_dayPlannerOnAction() {
            println("day planner")
        }
        override fun button_DayTrackerOnAction() {
            println("day tracker")
        }
        override fun button_habitTrackerOnAction() {
            println("habit tracker")
        }
    }

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/day_tracker/view.fxml"))
        val root = fxmlLoader.load<Parent>()
        val dayTrackerController: DayTrackerController = fxmlLoader.getController() as DayTrackerController
        dayTrackerController.subscribe(listener)
        val scene = Scene(root, 1920.0, 1013.0)
        stage.title = "Tayckner"
        stage.scene = scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TaycknerApplication::class.java)
        }
    }
}