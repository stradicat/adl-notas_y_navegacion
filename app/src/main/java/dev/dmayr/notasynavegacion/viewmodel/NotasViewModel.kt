package dev.dmayr.notasynavegacion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.dmayr.notasynavegacion.data.NotaBBDD
import dev.dmayr.notasynavegacion.model.Nota
import dev.dmayr.notasynavegacion.repository.NotaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotaRepository
    private val _cadenaDeBusqueda = MutableLiveData<String>("")

    val notas = MediatorLiveData<List<Nota>>()

    private val todasLasNotas: LiveData<List<Nota>>
    private var fuenteDeBusquedaActual: LiveData<List<Nota>>? = null

    init {
        val dao = NotaBBDD.getDatabase(application).notaDao()
        repository = NotaRepository(dao)
        todasLasNotas = repository.todasLasNotas

        crearFuente(todasLasNotas)

        _cadenaDeBusqueda.observeForever { consulta ->
            fuenteDeBusquedaActual?.let {
                notas.removeSource(it)
            }
            if (consulta.isBlank()) {
                notas.addSource(todasLasNotas) {
                    notas.value = it
                }
                fuenteDeBusquedaActual = todasLasNotas
            } else {
                val fuenteDeBusqueda = repository.buscarNotas(consulta)
                notas.addSource(fuenteDeBusqueda) {
                    notas.value = it
                }
                fuenteDeBusquedaActual = fuenteDeBusqueda
            }
        }
    }

    private fun crearFuente(nuevaFuente: LiveData<List<Nota>>) {
        if (fuenteDeBusquedaActual != null) {
            notas.removeSource(fuenteDeBusquedaActual!!)
        }
        fuenteDeBusquedaActual = nuevaFuente
        notas.addSource(nuevaFuente) {
            notas.value = it
        }
    }

    fun buscarNotas(consulta: String) {
        _cadenaDeBusqueda.value = consulta
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
