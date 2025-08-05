package dev.dmayr.notasynavegacion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dev.dmayr.notasynavegacion.databinding.ActivityDetalleNotaBinding
import dev.dmayr.notasynavegacion.model.Nota
import dev.dmayr.notasynavegacion.viewmodel.NotasViewModel
import kotlinx.coroutines.launch

class DetalleNotaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleNotaBinding

    private val viewModel: NotasViewModel by viewModels()

    private var nota: Nota? = null
    private var notaOriginalVacia = false

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        binding = ActivityDetalleNotaBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        val id = intent.getLongExtra("nota_id", -1L)
        lifecycleScope.launch {
            nota = viewModel.obtenerPorId(id)
            if (nota == null) return@launch finish()

            notaOriginalVacia = nota!!.tituloNota.isEmpty() && nota!!.contenidoNota.isEmpty()
            binding.etTitulo.setText(nota!!.tituloNota)
            binding.etContenido.setText(nota!!.contenidoNota)
        }

        binding.btnGuardar.setOnClickListener {
            guardarYSalir()
        }
        binding.btnEliminar.setOnClickListener {
            nota?.let {
                viewModel.deleteNota(it)
                Toast.makeText(this, "Nota eliminada", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun guardarYSalir() {
        val titulo = binding.etTitulo.text.toString().trim()
        val contenido = binding.etContenido.text.toString().trim()
        nota?.let {
            if (titulo.isNotEmpty() || contenido.isNotEmpty()) {
                it.tituloNota = titulo
                it.contenidoNota = contenido
                viewModel.updateNota(it)
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
            } else if (notaOriginalVacia) {
                viewModel.deleteNota(it)
            }
        }
        finish()
    }

    override fun onPause() {
        super.onPause()
        guardarYSalir()
    }
}
