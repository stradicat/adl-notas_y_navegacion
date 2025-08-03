package dev.dmayr.notasynavegacion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "titulo") var tituloNota: String,
    @ColumnInfo(name = "contenido") var contenidoNota: String,
    @ColumnInfo(name = "fechaCreacion") val fechaCreacion: Long = System.currentTimeMillis()
)
