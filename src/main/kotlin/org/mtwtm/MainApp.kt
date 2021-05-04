package org.mtwtm

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.mtwtm.data.User
import org.mtwtm.repository.UserRepository
import java.io.IOException
import java.util.*

class MainApp : Application() {
    private val strings: ResourceBundle = ResourceBundle.getBundle("strings")
    private val repository: UserRepository = UserRepository(DB)

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
        val vBox = createEvenVBox(navigateSearch, navigateCreate, INSET_50)
        stage.scene = Scene(vBox, APP_WIDTH, APP_HEIGHT)
        stage.show()
    }

    private fun startSearch(stage: Stage) {
        stage.title = strings.getString(SEARCH)
        val searchContent = TextField()
        val searchButton = Button()
        searchButton.text = strings.getString(SEARCH)
        val vBox = createEvenVBox(searchContent, searchButton, INSET_50)
        searchButton.onMouseClicked = EventHandler {
            val user = repository.search(searchContent.text)
            if (user == null) {
                showAlert(stage, strings.getString(NOT_FOUND), Alert.AlertType.WARNING)
            } else {
                showAlert(stage, user.toShortString(), Alert.AlertType.INFORMATION)
            }
            searchContent.clear()
            searchContent.requestFocus()
        }
        stage.scene = Scene(vBox, APP_WIDTH, APP_HEIGHT)
    }

    private fun startCreate(stage: Stage) {
        stage.title = strings.getString(CREATE)
        stage.scene = Scene(Pane(), APP_WIDTH, APP_HEIGHT)
        try {
            repository.insert(User("", "", "", "", System.currentTimeMillis()))
        } catch (ex: IOException) {
            showAlert(stage, strings.getString(CREATE_FAILURE), Alert.AlertType.ERROR)
            stage.close()
        }
        showAlert(stage, strings.getString(CREATE_SUCCESS), Alert.AlertType.INFORMATION)
        startMain(stage)
    }

    private fun showAlert(owner: Stage, title: String, type: Alert.AlertType) {
        val alert = Alert(type)
        alert.initOwner(owner)
        alert.contentText = title
        alert.showAndWait()
    }

    private fun createEvenVBox(regionA: Region, regionB: Region, margin: Insets?): VBox {
        val vBox = VBox()

        fun setProperties(region: Region) {
            margin?.let {
                VBox.setMargin(region, margin)
            }
            region.prefHeightProperty().bind(vBox.heightProperty().divide(2))
            region.prefWidthProperty().bind(vBox.widthProperty())
        }

        vBox.children.addAll(regionA, regionB)
        setProperties(regionA)
        setProperties(regionB)
        return vBox
    }

    companion object {
        const val APP_WIDTH = 640.0
        const val APP_HEIGHT = 320.0
        const val TITLE = "org.mtwtm.membersDirectory.title"
        const val SEARCH = "org.mtwtm.membersDirectory.mode.search"
        const val CREATE = "org.mtwtm.membersDirectory.mode.create"
        const val NOT_FOUND = "org.mtwtm.membersDirectory.notFound"
        const val CREATE_SUCCESS = "org.mtwtm.membersDirectory.createSuccess"
        const val CREATE_FAILURE = "org.mtwtm.membersDirectory.createFailure"
        val INSET_50 = Insets(50.0, 50.0, 50.0, 50.0)
        const val DB = "membersDB"
    }
}