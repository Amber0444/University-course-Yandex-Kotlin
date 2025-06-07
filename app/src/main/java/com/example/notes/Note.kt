package com.example.notes

import android.graphics.Color
import java.time.LocalDateTime
import java.util.UUID

data class Note constructor(
    val uuid: UUID,
    val title: String,
    val content: String,
    val color: Color,
    val importance: Importance,
    val date: LocalDateTime,
)