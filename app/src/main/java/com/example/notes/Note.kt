package com.example.notes

import android.graphics.Color
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
        fun build(block: Note.NoteBuilder.() -> Unit): Note =
            Note.NoteBuilder().apply(block).build()
    }

    class NoteBuilder {
        lateinit var title: String
        lateinit var content: String
        var color: Int = Color.WHITE;
        var importance: Importance = Importance.COMMON

        fun build(): Note {
            requireNotNull(title) { "Title is required!" }
            requireNotNull(content) { "Content is required!" }

            return Note(
                UUID.randomUUID(),
                title,
                content,
                color,
                importance,
                LocalDateTime.now().plusDays(3)
            )
        }
    }
}