package com.example.notes

import android.graphics.Color
import com.google.gson.JsonObject
import java.time.LocalDateTime
import java.util.*


data class Note(
    val uuid: UUID,
    val title: String,
    val content: String,
    val color: Int,
    val importance: Importance,
    val dateOfDelete: LocalDateTime,
) {
    companion object {
        fun Note.parseJsonObject(jsonObject: JsonObject): Note {
            val parsedUuid: UUID = UUID.fromString(jsonObject.get("uuid").asString)
            val parsedTitle: String = jsonObject.get("title").asString
            val parsedContent: String = jsonObject.get("content").asString
            val parsedColor = getColorFromJsonObject(jsonObject)
            val parsedImportance = getImportanceFromJsonObject(jsonObject)
            val parsedDateOfDelete: LocalDateTime = LocalDateTime.parse(jsonObject.get("date").asString)

            return Note.build {
                uuid = parsedUuid
                title = parsedTitle
                content = parsedContent
                color = parsedColor
                importance = parsedImportance
                dateOfDelete = parsedDateOfDelete
            }
        }

        private fun getColorFromJsonObject(jsonObject: JsonObject): Int {
            var color = Color.WHITE
            if (jsonObject.has("color")) {
                val argbList: List<String> = jsonObject.get("color").asString.split(",")

                color = Color.argb(argbList[0].toInt(), argbList[1].toInt(),
                    argbList[2].toInt(), argbList[3].toInt())
            }
            return color
        }

        private fun getImportanceFromJsonObject(jsonObject: JsonObject): Importance {
            var importance = Importance.COMMON
            if (jsonObject.has("importance")) {
                importance = Importance.valueOf(jsonObject.get("importance").asString)
            }
            return importance
        }

        fun build(block: Note.NoteBuilder.() -> Unit): Note =
            Note.NoteBuilder().apply(block).build()
    }

    class NoteBuilder {
        var uuid: UUID = UUID.randomUUID()
        lateinit var title: String
        lateinit var content: String
        var color: Int = Color.WHITE;
        var importance: Importance = Importance.COMMON
        var dateOfDelete: LocalDateTime = LocalDateTime.now().plusDays(3)

        fun build(): Note {
            requireNotNull(title) { "Title is required!" }
            requireNotNull(content) { "Content is required!" }

            return Note(
                uuid,
                title,
                content,
                color,
                importance,
                dateOfDelete,
            )
        }
    }

    fun toJson(): JsonObject{
        val jsonObject = JsonObject()

        jsonObject.addProperty("uuid", uuid.toString())
        jsonObject.addProperty("title", title)
        jsonObject.addProperty("content", content)
        if (color != Color.WHITE) {
            jsonObject.addProperty("color", listOf(
                Color.alpha(color),
                Color.red(color),
                Color.green(color),
                Color.blue(color)).toString()) }
        if (importance != Importance.COMMON) { jsonObject.addProperty(
            "importance", importance.toString())}
        jsonObject.addProperty("dateOfDelete", dateOfDelete.toString())

        return jsonObject
    }
}