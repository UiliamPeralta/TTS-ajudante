package com.example.uiliam.ttsvozajudante.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palavra")
data class Palavra(@PrimaryKey(autoGenerate = true) val id: Int = 0, var palavra: String)

enum class Action {EDITAR, EXCLUIR}

