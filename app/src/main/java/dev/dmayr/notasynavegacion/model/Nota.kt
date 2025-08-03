package dev.dmayr.notasynavegacion.model

data class Nota(
    val id: Long,
    var tituloNota: String,
    var contenidoNota: String,
    val fechaCreacion: Long = System.currentTimeMillis()
)
