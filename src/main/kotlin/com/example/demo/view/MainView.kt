package com.example.demo.view

import com.example.demo.app.MyTab
import com.example.demo.app.tabs
import javafx.scene.control.TabPane
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*
import java.io.File
class MainView : View("Hello TornadoFX") {
    override val root = VBox()
    var tabPane: TabPane? = null
    init {
        primaryStage.setOnCloseRequest {
            println("关闭")
            System.exit(0)
        }
        addStageIcon(Image(MainView::class.java.getResourceAsStream("/tm-dog.png")))
        with(root) {
            menubar {
                menu("File") {
                    menu("Connect") {
                        item("Facebook").action { println("Connecting Facebook!") }
                        item("Twitter").action { println("Connecting Twitter!") }
                    }
                    separator()
                    item("Save", "Shortcut+S").action {
                        println("Saving!")
                    }
                    item("Quit", "Shortcut+Q").action {
                        println("Quitting!")
                    }
                }
                menu("New") {
                    item("新的tab", "Shortcut+N").action {
                        MyTab().tabs(tabPane!!, "new")
                    }
                }
            }
            tabPane = tabpane {
                //tabs("new")
                hgrow = Priority.ALWAYS
            }


            val file = File("./src/main/resources/tabs/")
            file.listFiles()?.forEach {
                MyTab().tabs(tabpane = tabPane!!,pathFile = it)
            }
        }
    }
}
