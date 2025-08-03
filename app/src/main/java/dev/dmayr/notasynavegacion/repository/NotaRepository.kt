package dev.dmayr.notasynavegacion.repository

import androidx.lifecycle.LiveData
import dev.dmayr.notasynavegacion.data.NotaDao
import dev.dmayr.notasynavegacion.model.Nota

class NotaRepository(private val dao: NotaDao) {
    val allNotas: LiveData<List<Nota>> = dao.getAll()

    suspend fun getById(id: Long): Nota? = dao.findById(id)

    suspend fun insert(nota: Nota): Long = dao.insert(nota)
    suspend fun update(nota: Nota) = dao.update(nota)
    suspend fun delete(nota: Nota) = dao.delete(nota)
}
