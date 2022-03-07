package pl.gawor.taycknerdesktopclient

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class TaycknerApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(TaycknerApplication::class.java.getResource("view/habit_tracker/view.fxml"))
        val scene = Scene(fxmlLoader.load(), 1920.0, 1013.0)
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