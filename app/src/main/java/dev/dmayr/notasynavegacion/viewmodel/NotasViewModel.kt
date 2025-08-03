package dev.dmayr.notasynavegacion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.dmayr.notasynavegacion.data.NotaBBDD
import dev.dmayr.notasynavegacion.model.Nota
import dev.dmayr.notasynavegacion.repository.NotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotaRepository
    val notas: LiveData<List<Nota>>

    init {
        val dao = NotaBBDD.getDatabase(application).notaDao()
        repository = NotaRepository(dao)
        notas = repository.allNotas
    }

    suspend fun agregarNota(nota: Nota): Long {
        return repository.insert(nota)
    }

    fun updateNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(nota)
    }

    fun deleteNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(nota)
    }

    suspend fun obtenerPorId(id: Long): Nota? = repository.getById(id)
}
