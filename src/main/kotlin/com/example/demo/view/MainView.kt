package com.example.demo.view

import javafx.collections.FXCollections
import javafx.scene.control.TabPane
import javafx.geometry.Orientation.*
import javafx.scene.Scene
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import jdk.nashorn.internal.objects.Global.Infinity
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = VBox()
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
                        println("Pasting!")
                    }
                }
            }
            class Link(val name: String, val uri: String)
            class Person(name: String, nick: String) {
                var name by property<String>()
                fun nameProperty() = getProperty(Person::name)
                var nick by property<String>()
                fun nickProperty() = getProperty(Person::nick)

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
            val hhh = hbox {
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
                            column("Name", Person::nameProperty)
                            column("Nick", Person::nickProperty)
                            columnResizePolicy = SmartResize.POLICY
                        }
                    }
                }
                val ttt = tabpane {
                    tab("Screen-1") {
                        vbox {
                            button("Button 1")
                            button("Button 2")
                        }
                    }
                    hgrow = Priority.ALWAYS
                }
                ttt.tab("Screen-2") {
                    form {
                        fieldset("Feedback Form", labelPosition = VERTICAL) {
                            button("Send") {
                                action {
                                    ttt.tab("nmka")
                                }
                            }
                            field("Comment", VERTICAL) {
                                textarea {
                                    prefRowCount = 5
                                    vgrow = Priority.ALWAYS
                                }
                            }
                            buttonbar {
                                button("Send") {
                                    action {
                                        ttt.tab("nmka")
                                    }
                                }
                            }
                        }
                    }
                }
                prefWidth = 800.0
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