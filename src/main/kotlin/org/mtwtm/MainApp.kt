package org.mtwtm

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.text.Text
import javafx.stage.Stage
import java.util.*


class MainApp : Application() {

    override fun start(stage: Stage) {
        val strings = ResourceBundle.getBundle("strings")
        stage.title = "JavaFX and Gradle";
        val text = Text(strings.getString("org.mtwtm.hello"))
        stage.scene = Scene(StackPane(text), 1280.0, 640.0);
        stage.show();
    }
}