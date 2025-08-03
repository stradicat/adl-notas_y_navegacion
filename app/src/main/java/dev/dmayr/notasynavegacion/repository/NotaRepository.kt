package dev.dmayr.notasynavegacion.repository

import androidx.lifecycle.LiveData
import dev.dmayr.notasynavegacion.data.NotaDao
import dev.dmayr.notasynavegacion.model.Nota

class NotaRepository(private val dao: NotaDao) {
    val todasLasNotas: LiveData<List<Nota>> = dao.getAll()

    suspend fun encontrarPorId(id: Long): Nota? = dao.findById(id)

    suspend fun insertar(nota: Nota): Long = dao.insert(nota)
    suspend fun modificar(nota: Nota) = dao.update(nota)
    suspend fun eliminar(nota: Nota) = dao.delete(nota)
    suspend fun eliminarTodas() = dao.deleteAll()
}
