package com.example.zametkafigma4

import java.io.Serializable
import java.util.UUID

data class NotesModel(
    val notesId: String = UUID.randomUUID().toString(),
    val noteTitle: String,
    val noteDescription: String,
): Serializable
