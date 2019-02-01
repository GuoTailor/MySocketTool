package com.example.demo.app

import com.example.demo.common.readRequestByFile
import com.example.demo.common.saverRequestToFile
import com.example.demo.view.MainView
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.GridPane.setConstraints
import tornadofx.*
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.web.WebView
import java.io.File


/**
 * Created by GYH on 2019/1/25.
 */
fun TabPane.tabs(text: String? = null, pathFile: File? = null) {
    val request: Request?
    if (pathFile != null) {
        request = readRequestByFile(pathFile)
    }else {
        request = Request()
        request.heads.let { if (it.size == 0) it.add(HeadField(true, "02", "byte")) }
    }
    val name = "${request.ip}-${request.port}-${request.code}"
    var path = pathFile

    var web: WebView? = null
    var t: Tab? = null
    t = tab(text ?: name) {
        vbox {
            gridpane {
                vgap = 4.0
                hgap = 10.0
                paddingAll = 5.0
                label("ip: ") {
                    setConstraints(this, 0, 0)
                }
                val ip = textfield(request.ip) {
                    setConstraints(this, 1, 0)
                }
                label("port: ") {
                    setConstraints(this, 2, 0)
                }
                val port = textfield(request.port.toString()) {
                    prefColumnCount = 4
                    setConstraints(this, 3, 0)
                }
                label("msgCode: ") {
                    setConstraints(this, 4, 0)
                }
                val code = textfield(request.code.toString()) {
                    prefColumnCount = 4
                    setConstraints(this, 5, 0)
                    textProperty().addListener { observable: ObservableValue<out String>, oldValue: String, newValue: String ->
                        println(newValue)
                        web?.engine?.executeScript("test1('\"code\": $newValue')")
                    }
                }
                button("连接") {
                    setConstraints(this, 6, 0)
                    action {
                        request.ip = ip.text
                        request.port = port.text.toInt()
                        request.code = code.text.toInt()
                        t?.text = "${ip.text}-${port.text}-${code.text}"
                        request.heads.forEach {
                            println(it.content)
                            println(it.chosen.isSelected)
                            println(it.type.value)
                        }
                        web?.engine?.executeScript("test1('\"code\": ${request.code}')")
                    }
                }
            }
            tabpane {
                tab(" head ") {
                    tableview(request.heads) {
                        isEditable = true
                        column("选中", HeadField::chosen) {
                            cellValueFactory = PropertyValueFactory("chosen")
                        }
                        column("内容", HeadField::content) {
                            onEditCommit = EventHandler { t: TableColumn.CellEditEvent<HeadField, String> ->
                                val size = t.tableView.items.size
                                println("$size ${t.tablePosition.row}")
                                if (size - 1 == t.tablePosition.row) {
                                    request.heads.add(HeadField(true, "00", "byte"))
                                }
                                (t.tableView.items[t.tablePosition.row] as HeadField).content = t.newValue
                            }
                        }.cellFactory = TextFieldTableCell.forTableColumn<HeadField>()
                        column("类型", HeadField::type) {
                            /*val nmka = onEditCancel
                            selectionModel.selectedIndexProperty().addListener { ov, oldv, newv ->
                            }*/
                        }
                    }.isEditable = true
                }
                tab(" body ") {
                    form {
                        fieldset(labelPosition = Orientation.VERTICAL) {
                            web = webview {
                                prefHeight = 400.px.value
                                val url = MainView::class.java.getResource("/index.html").toExternalForm()
                                println(url)
                                engine.load(url)
                            }

                            buttonbar {
                                button("Send") {
                                    action {
                                        val obj = web?.engine?.executeScript("test()")
                                        println(obj)
                                        request.body = obj as String
                                        path = saverRequestToFile(request, "./src/main/resources/tabs/${t?.text}", path)
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