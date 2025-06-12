package com.example.notes

import android.content.ContentValues
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File
import java.time.LocalDateTime
import java.util.UUID

fun main() {
    val note = Note.build {
        title = "a"
        content = "a"
    }

    val json = note.toJson()
    val file = File("json")
    file.writeText(json.toString())


    println(json)
}