package org.mtwtm

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.text.Text
import javafx.stage.Stage
import org.mtwtm.data.User
import java.io.*
import java.util.*


class MainApp : Application() {
    private val db: Map<String, User> = mapOf()

    private fun initDB() {
        //val f = File(DB_FILE_NAME)
        //f.takeIf { it.exists() }?.createNewFile()
        val bw = BufferedWriter(FileWriter(DB_FILE_NAME))
        bw.write("HW")
        bw.close()
        //val br = BufferedReader(FileReader(DB_FILE_NAME))
    }

    override fun start(stage: Stage) {
        initDB()
        val strings = ResourceBundle.getBundle("strings")
        stage.title = "JavaFX and Gradle";
        val br = BufferedReader(FileReader(DB_FILE_NAME))
//        val text = Text(strings.getString("org.mtwtm.hello"))
        val text = Text(br.readLine())
        stage.scene = Scene(StackPane(text), 1280.0, 640.0);
        stage.show();
    }

    companion object {
        const val DB_FILE_NAME = "members.db"
    }
}