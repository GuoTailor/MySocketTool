package com.example.demo.view

import com.example.demo.app.tabs
import com.example.demo.common.readRequestByFile
import javafx.collections.FXCollections
import javafx.geometry.Orientation
import javafx.geometry.Orientation.VERTICAL
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.web.WebView
import tornadofx.*
import java.io.File

class MainView : View("Hello TornadoFX") {
    override val root = VBox()
    var tabPane: TabPane? = null

    init {
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
                menu("Edit") {
                    item("Copy", "Shortcut+C").action {
                        println("Copying!")
                    }
                    item("Paste", "Shortcut+V").action {
                        println("Pasting!${"3".compareTo("2")}")
                    }
                }
                menu("New") {
                    item("新的tab", "Shortcut+N").action {
                        tabPane?.tabs("new")
                    }
                }
            }
            class Link(val name: String, val uri: String)
            class Person(name: String, nick: String) {
                var name by property<String>()
                var nick by property<String>()

                init {
                    this.name = name
                    this.nick = nick
                }
            }

            val links = listOf(Link("Alpha", "https://upload.jianshu.io/users/upload_avatars/3764796/62b7b022ddd1?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96"),
                    Link("Gamma", "https://upload-images.jianshu.io/upload_images/3764796-403dfbc595c3f606.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/374/format/webp")
            ).observable()
            val people = FXCollections.observableArrayList(
                    Person("1", "Samantha Stuart"),
                    Person("2", "Tom Marks"),
                    Person("3", "Stuart Gills"),
                    Person("3", "Nicole Williams")
            )
            anchorpane {
                vgrow = Priority.ALWAYS
                splitpane {
                    //anchorpane {
                    drawer {
                        item("Links") {
                            listview(links) {
                                cellFormat { link ->
                                    graphic = hyperlink(link.name) {
                                        setOnAction {
                                            hostServices.showDocument(link.uri)
                                        }
                                    }
                                }
                            }
                        }
                        item("People") {
                            tableview(people) {
                                column("Name", Person::name)
                                column("Nick", Person::nick)
                                columnResizePolicy = SmartResize.POLICY
                            }
                        }
                    }
                    //}
                    //anchorpane {
                    vgrow = Priority.ALWAYS
                    tabPane = tabpane {
                        tabs("new")
                        hgrow = Priority.ALWAYS
                    }
                    //}
                }
            }
        }

        val file = File("./src/main/resources/tabs/")
        file.listFiles()?.forEach {
            tabPane?.tabs(pathFile = it)
        }
    }
}
