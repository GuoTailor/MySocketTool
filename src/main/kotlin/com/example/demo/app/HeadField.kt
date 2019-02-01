package com.example.demo.app

import com.fasterxml.jackson.annotation.JsonIgnore
import javafx.collections.FXCollections
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import tornadofx.*


/**
 * Created by gyh on 2019/1/28.
 */
class HeadField(chosen: Boolean = false, content: String, type: String) {
    var chosen: CheckBox by property()
    var content: String by property()
    var type: ChoiceBox<String> by property()

    fun getContent(count: Int = 0): List<Byte> {
        val list = content.split(",")
        return list.stream().map {
            val i = it.trim().toInt(16)
            when(type.value) {
                "byte" -> listOf(i.toByte())
                "short" -> listOf(i.ushr(8).toByte(), i.toByte())
                "int" -> listOf(i.ushr(24).toByte(), i.ushr(16).toByte(), i.ushr(8).toByte(), i.toByte())
                "count" -> listOf(count.ushr(8).toByte(), count.toByte())
                else -> throw IllegalStateException("类型错误")
            }
        }.collect({ArrayList()}, { sb, s -> sb.addAll(s)}, { sb, sb2 -> sb.addAll(sb2)})
    }

    init {
        this.chosen = CheckBox()
        this.chosen.isSelected = chosen
        this.content = content
        val cursors = FXCollections.observableArrayList<String>(
                "byte",
                "short",
                "int",
                "count"
        )
        this.type = ChoiceBox(cursors)
        this.type.value = type
    }

    fun serialize(): Map<String, Any> {
        return mapOf(
                "chosen" to chosen.isSelected,
                "content" to content,
                "type" to type.value
        )
    }

}

fun deserializeToHead(map: Map<String, Any>): HeadField {
    return HeadField(map["chosen"] as Boolean, map["content"] as String, map["type"] as String)
}
