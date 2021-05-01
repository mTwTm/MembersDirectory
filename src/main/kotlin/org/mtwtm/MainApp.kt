package org.mtwtm

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class MainApp : Application() {

    override fun start(stage: Stage) {
        stage.title = "JavaFX and Gradle";
        stage.scene = Scene(StackPane(), 1280.0, 640.0);
        stage.show();
    }
}