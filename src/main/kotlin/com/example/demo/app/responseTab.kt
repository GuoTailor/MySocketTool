package com.example.demo.app

import com.example.demo.view.MainView
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.TextFieldTableCell
import tornadofx.*

/**
 * Created by GYH on 2019/2/1.
 */
fun TabPane.tabs(request: Request) {
    tab(" head ") {
        tableview(request.heads) {
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
                webview {
                    prefHeight = 400.px.value
                    val url = MainView::class.java.getResource("/index.html").toExternalForm()
                    println(url)
                    engine.load(url)
                }
            }
        }
    }
}

