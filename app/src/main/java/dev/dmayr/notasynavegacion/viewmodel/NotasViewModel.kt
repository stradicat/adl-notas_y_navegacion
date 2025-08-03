package dev.dmayr.notasynavegacion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.dmayr.notasynavegacion.data.NotasManager
import dev.dmayr.notasynavegacion.model.Nota

class NotasViewModel : ViewModel() {

    private val _notas = MutableLiveData<List<Nota>>()
    val notas: LiveData<List<Nota>> = _notas

    init {
        cargarNotas()
    }

    fun cargarNotas() {
        _notas.value = NotasManager.obtenerNotas()
    }

    fun agregarNota(nota: Nota) {
        NotasManager.agregarNota(nota)
        cargarNotas()
    }

    fun eliminarNota(id: Long) {
        NotasManager.eliminarNota(id)
        cargarNotas()
    }

    fun buscarNotas(query: String) {
        _notas.value = NotasManager.buscarNotas(query)
    }

    fun obtenerNotaPorId(id: Long): Nota? = NotasManager.obtenerNotaPorId(id)

    fun actualizarNota(nota: Nota) {
        NotasManager.actualizarNota(nota)
        cargarNotas()
    }
}
