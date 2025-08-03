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
        notas = repository.todasLasNotas
    }

    suspend fun agregarNota(nota: Nota): Long {
        return repository.insertar(nota)
    }

    fun updateNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.modificar(nota)
    }

    fun deleteNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.eliminar(nota)
    }

    fun eliminarTodas() = viewModelScope.launch(Dispatchers.IO) {
        repository.eliminarTodas()
    }

    suspend fun obtenerPorId(id: Long): Nota? = repository.encontrarPorId(id)
}
