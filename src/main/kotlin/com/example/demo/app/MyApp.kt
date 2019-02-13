package com.example.demo.app

import com.example.demo.view.MainView
import javafx.stage.Stage
import tornadofx.*

/**
 * Created by GYH on 2019/2/13.
 */
class MyApp: App(MainView::class)

fun main() {
    launch<MyApp>()
}