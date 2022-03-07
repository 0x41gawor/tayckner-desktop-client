package pl.gawor.taycknerdesktopclient

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import pl.gawor.taycknerdesktopclient.controller.Observer.INavigationSubscriber
import pl.gawor.taycknerdesktopclient.controller.dayplanner.DayPlannerController
import pl.gawor.taycknerdesktopclient.controller.daytracker.DayTrackerController
import pl.gawor.taycknerdesktopclient.controller.habittracker.HabitTrackerController

class TaycknerApplication : Application() {

    // P R O P E R T I E S

    private val SIZE_X = 1920.0
    private val SIZE_Y = 1013.0
    
    private lateinit var  stage: Stage

    private val listener = object : INavigationSubscriber {
        override fun button_dayPlannerOnAction() {
            stage.scene = dayPlannerScene()
        }
        override fun button_DayTrackerOnAction() {
            stage.scene = dayTrackerScene()
        }
        override fun button_habitTrackerOnAction() {
            stage.scene = habitTrackerScene()
        }
    }

    // F U N C T I O N S

    override fun start(stage: Stage) {
        this.stage = stage
        this.stage.title = "Tayckner"
        this.stage.scene = dayTrackerScene()
        this.stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TaycknerApplication::class.java)
        }
    }

    private fun dayTrackerScene(): Scene {
        val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/day_tracker/view.fxml"))
        val root = fxmlLoader.load<Parent>()
        val controller: DayTrackerController = fxmlLoader.getController() as DayTrackerController
        controller.subscribe(listener)
        return Scene(root, SIZE_X, SIZE_Y)
    }

    private fun dayPlannerScene(): Scene {
        val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/day_planner/view.fxml"))
        val root = fxmlLoader.load<Parent>()
        val controller: DayPlannerController = fxmlLoader.getController() as DayPlannerController
        controller.subscribe(listener)
        return Scene(root, SIZE_X, SIZE_Y)
    }

    private fun habitTrackerScene(): Scene {
        val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/habit_tracker/view.fxml"))
        val root = fxmlLoader.load<Parent>()
        val controller: HabitTrackerController = fxmlLoader.getController() as HabitTrackerController
        controller.subscribe(listener)
        return Scene(root, SIZE_X, SIZE_Y)
    }
}