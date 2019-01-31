package com.example.demo.view

import com.example.demo.app.tabs
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.concurrent.Worker
import javafx.geometry.Orientation
import javafx.scene.control.TabPane
import javafx.geometry.Orientation.*
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.web.WebView
import netscape.javascript.JSObject
import tornadofx.*
import java.io.File

class MainView : View("Hello TornadoFX") {
    override val root = VBox()
    var tabPane: TabPane? = null
    var tabid = 2

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
                        tabPane?.tabs("new", "${tabid++}")
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
                splitpane (Orientation.VERTICAL){
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
                            var webebgine: WebView? = null
                            tabPane = tabpane {
                                tabs("new", "${tabid++}")
                                tab("Screen-1") {
                                    id = "3"
                                    vbox {
                                        button("Button 1")
                                        button("Button 2") {
                                            action {
                                                val obj = webebgine?.engine?.executeScript("test()")
                                                println(obj)
                                            }
                                        }
                                        webebgine = webview {
                                            prefHeight = 400.px.value
                                            val url = MainView::class.java.getResource("/index.html").toExternalForm()
                                            println(url)
                                            engine.load(url)
                                        }
                                    }
                                }
                                hgrow = Priority.ALWAYS
                            }
                            tabPane?.tab("Screen-2") {
                                id = "1"
                                form {
                                    fieldset("Feedback Form", labelPosition = VERTICAL) {
                                        field("Comment", VERTICAL) {
                                            textarea {
                                                prefRowCount = 5
                                                vgrow = Priority.ALWAYS
                                            }
                                        }
                                        buttonbar {
                                            button("Send") {
                                                action {
                                                    tabPane?.tab("nmka")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //}
                        }
                    }
                    tabpane {
                        tab("Screen 1") {
                            vbox {
                                button("Button 1")
                                button("Button 2")
                            }
                        }
                        tab("Screen 2") {
                            form {
                                fieldset("Feedback Form", labelPosition = VERTICAL) {
                                    field("Comment", VERTICAL) {
                                        textarea {
                                            prefRowCount = 5
                                            vgrow = Priority.ALWAYS
                                        }
                                    }
                                    buttonbar {
                                        button("Send")
                                    }
                                }
                            }
                        }
                    }.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                }
            }
        }
    }
}
