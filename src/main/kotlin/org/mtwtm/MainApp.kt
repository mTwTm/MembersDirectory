package org.mtwtm

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.mtwtm.data.User
import org.mtwtm.repository.UserRepository
import java.io.IOException
import java.lang.StringBuilder
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
            val userList = repository.search(searchContent.text)
            if (userList.isEmpty()) {
                showAlert(stage, strings.getString(NOT_FOUND), Alert.AlertType.WARNING)
            } else {
                showAlert(stage, userList.toUserListString(), Alert.AlertType.INFORMATION)
            }
            searchContent.clear()
            searchContent.requestFocus()
        }
        stage.scene = Scene(vBox, APP_WIDTH, APP_HEIGHT)
    }

    private fun startCreate(stage: Stage) {
        val cardLabel = Label(strings.getString(FIELD_CARD))
        val cardIdField = TextField()
        val cardHBox = createEvenHBox(cardLabel, cardIdField, INSET_20)
        val nameLabel = Label(strings.getString(FIELD_NAME))
        val nameField = TextField()
        val nameHBox = createEvenHBox(nameLabel, nameField, INSET_20)
        val phoneLabel = Label(strings.getString(FIELD_PHONE))
        val phoneField = TextField()
        val phoneHBox = createEvenHBox(phoneLabel, phoneField, INSET_20)
        val otherLabel = Label(strings.getString(FIELD_OTHER))
        val otherField = TextField()
        val otherHBox = createEvenHBox(otherLabel, otherField, INSET_20)
        val button = Button(strings.getString(CREATE))
        val vBox = VBox()
        vBox.children.addAll(cardHBox, nameHBox, phoneHBox, otherHBox, button)
        stage.title = strings.getString(CREATE)
        stage.scene = Scene(vBox, APP_WIDTH, APP_HEIGHT)
        button.onMouseClicked = EventHandler {
            if (checkInputNonEmpty(cardIdField, nameField, phoneField)) {
                showAlert(stage, strings.getString(EMPTY_FIELD), Alert.AlertType.WARNING)
            } else {
                insertUser(stage,
                    User(
                        cardIdField.text,
                        nameField.text,
                        phoneField.text,
                        otherField.text,
                        System.currentTimeMillis()
                    )
                )
            }
        }
    }

    private fun checkInputNonEmpty(
        cardIdField: TextField,
        nameField: TextField,
        phoneField: TextField
    ): Boolean {
        return cardIdField.text.isEmpty() || nameField.text.isEmpty() || phoneField.text.isEmpty()
    }

    private fun insertUser(stage: Stage, user: User) {
        try {
            repository.insert(user)
        } catch (ex: IOException) {
            showAlert(stage, strings.getString(CREATE_FAILURE), Alert.AlertType.ERROR)
            stage.close()
            return
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

    private fun createEvenHBox(regionA: Region, regionB: Region, margin: Insets?): HBox {
        val hBox = HBox()

        fun setProperties(region: Region) {
            margin?.let {
                VBox.setMargin(region, margin)
            }
            region.prefHeightProperty().bind(hBox.heightProperty())
            region.prefWidthProperty().bind(hBox.widthProperty().divide(2))
        }

        hBox.children.addAll(regionA, regionB)
        setProperties(regionA)
        setProperties(regionB)
        return hBox
    }

    private fun List<User>.toUserListString(): String = foldIndexed(StringBuilder()) { i, sb, u ->
        sb.append('(').append(i).append(")\n")
            .append(u.toUserString()).append('\n')
    }.toString()

    companion object {
        const val APP_WIDTH = 640.0
        const val APP_HEIGHT = 320.0
        const val TITLE = "org.mtwtm.membersDirectory.title"
        const val SEARCH = "org.mtwtm.membersDirectory.mode.search"
        const val CREATE = "org.mtwtm.membersDirectory.mode.create"
        const val NOT_FOUND = "org.mtwtm.membersDirectory.notFound"
        const val CREATE_SUCCESS = "org.mtwtm.membersDirectory.createSuccess"
        const val CREATE_FAILURE = "org.mtwtm.membersDirectory.createFailure"
        const val FIELD_CARD = "org.mtwtm.membersDirectory.field.card"
        const val FIELD_NAME = "org.mtwtm.membersDirectory.field.name"
        const val FIELD_PHONE = "org.mtwtm.membersDirectory.field.phone"
        const val FIELD_OTHER = "org.mtwtm.membersDirectory.field.other"
        const val EMPTY_FIELD = "org.mtwtm.membersDirectory.emptyField"
        val INSET_50 = Insets(50.0, 50.0, 50.0, 50.0)
        val INSET_20 = Insets(20.0, 20.0, 20.0, 20.0)
        const val DB = "membersDB"
    }
}