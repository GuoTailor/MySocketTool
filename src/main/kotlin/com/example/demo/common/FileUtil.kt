package com.example.demo.common

import com.example.demo.app.Request
import com.example.demo.app.deserializeToRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

/**
 * Created by GYH on 2019/2/1.
 */
val mapper = jacksonObjectMapper()

fun saverRequestToFile(data: Request, path: String, name: File? = null): File {
    var file: File?
    if (name == null) {
        var i = 0
        file = File("$path.json")
        while (file!!.exists()) {
            file = File("$path-${i++}.json")
        }
    }else {
        file = name
    }
    mapper.writeValue(file, data.serialize())
    return file
}

fun readRequestByFile(path: File): Request {
    val map: Map<String, Any> = mapper.readValue(path)
    return deserializeToRequest(map)
}