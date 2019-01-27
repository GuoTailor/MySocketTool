package com.example.demo.app

import com.example.demo.view.MainView
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import javafx.scene.layout.GridPane.setConstraints
import tornadofx.*
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.StringConverter
import kotlin.reflect.KClass


/**
 * Created by GYH on 2019/1/25.
 */
fun TabPane.tabs(text: String? = null, id: String? = null) {
    val request = Request()
    request.heads.add(Head(true, "02", Byte::class))
    tab(text + id) {
        this.id = id
        vbox {
            gridpane {
                vgap = 4.0
                hgap = 10.0
                paddingAll = 5.0
                label("ip: ") {
                    setConstraints(this, 0, 0)
                }
                val ip = textfield {
                    setConstraints(this, 1, 0)
                }
                label("port: ") {
                    setConstraints(this, 2, 0)
                }
                val port = textfield {
                    prefColumnCount = 4
                    setConstraints(this, 3, 0)
                }
                label("msgCode: ") {
                    setConstraints(this, 2, 0)
                }
                val code = textfield {
                    prefColumnCount = 4
                    setConstraints(this, 3, 0)
                }
                button("连接") {
                    setConstraints(this, 4, 0)
                    action {
                        request.ip = ip.text
                        request.port = port.text.toInt()
                        request.code = code.text.toInt()
                    }
                }
            }
            tabpane {
                tab(" head ") {
                    tableview(request.heads) {
                        isEditable = true
                        column("选中", Head<*>::chosen)
                        column("内容", Head<*>::content){
                            onEditCommit = EventHandler { t: TableColumn.CellEditEvent<Head<*>, String> ->
                                val size = t.tableView.items.size
                                println("$size ${t.tablePosition.row}")
                                if (size - 1 == t.tablePosition.row) {
                                    request.heads.add(Head(true, "", Byte::class))
                                }
                                (t.tableView.items[t.tablePosition.row] as Head<*>).content = t.newValue
                            }
                        }.cellFactory = TextFieldTableCell.forTableColumn<Head<*>>()
                        column("类型", Head<*>::type){
                            cellFactory = TextFieldTableCell.forTableColumn<Head<*>, KClass<out Number>>((object : StringConverter<KClass<out Number>>(){
                                override fun toString(objec: KClass<out Number>): String {
                                    return objec.simpleName.toString()
                                }
                                override fun fromString(string: String): KClass<out Number> {
                                    return when(string) {
                                        "byte" -> Byte::class
                                        else -> Number::class
                                    }
                                }
                            }))
                        }
                    }.isEditable = true
                }
                tab(" body ") {
                    form {
                        fieldset(labelPosition = Orientation.VERTICAL) {
                            val web = webview {
                                prefHeight = 400.px.value
                                val url = MainView::class.java.getResource("/index.html").toExternalForm()
                                println(url)
                                engine.load(url)
                            }

                            buttonbar {
                                button("Send") {
                                    action {
                                        val obj = web.engine?.executeScript("test()")
                                        println(obj)
                                    }
                                }
                            }
                        }
                    }
                }
            }.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        }
    }
}