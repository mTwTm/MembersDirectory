package org.mtwtm

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

class MainApp : Application() {
    private val strings: ResourceBundle = ResourceBundle.getBundle("strings")

    override fun start(stage: Stage) {
        startMain(stage)
    }

    private fun startMain(stage: Stage) {
        stage.title = strings.getString(TITLE)
        val navigateSearch = Button(strings.getString(SEARCH))
        val navigateCreate = Button(strings.getString(CREATE))
        navigateSearch.onMouseClicked = EventHandler {
            startSearch(stage)
        }
        navigateCreate.onMouseClicked = EventHandler {
            startCreate(stage)
        }
        val vBox = VBox()
        vBox.children.addAll(navigateCreate, navigateSearch)
        VBox.setMargin(navigateCreate, INSET_50)
        VBox.setMargin(navigateSearch, INSET_50)

        navigateCreate.prefHeightProperty().bind(vBox.heightProperty().divide(2))
        navigateCreate.prefWidthProperty().bind(vBox.widthProperty())
        navigateSearch.prefHeightProperty().bind(vBox.heightProperty().divide(2))
        navigateSearch.prefWidthProperty().bind(vBox.widthProperty())
        stage.scene = Scene(vBox, WIDTH, HEIGHT)
        stage.show()
    }

    private fun startSearch(stage: Stage) {
        stage.title = strings.getString(SEARCH)
        stage.scene = Scene(Pane(), WIDTH, HEIGHT)
    }

    private fun startCreate(stage: Stage) {
        stage.title = strings.getString(CREATE)
        stage.scene = Scene(Pane(), WIDTH, HEIGHT)
    }

    companion object {
        const val WIDTH = 1280.0
        const val HEIGHT = 640.0
        const val TITLE = "org.mtwtm.membersDirectory.title"
        const val SEARCH = "org.mtwtm.membersDirectory.mode.search"
        const val CREATE = "org.mtwtm.membersDirectory.mode.create"
        val INSET_50 = Insets(50.0, 50.0, 50.0, 50.0)
    }
}