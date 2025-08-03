package dev.dmayr.notasynavegacion

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dev.dmayr.notasynavegacion.databinding.ActivityDetalleNotaBinding
import dev.dmayr.notasynavegacion.model.Nota
import dev.dmayr.notasynavegacion.viewmodel.NotasViewModel

class DetalleNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleNotaBinding
    private lateinit var viewModel: NotasViewModel
    private var nota: Nota? = null
    private var notaOriginalVacia = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[NotasViewModel::class.java]

        val idNota = intent.getLongExtra("nota_id", -1)
        nota = viewModel.obtenerNotaPorId(idNota)

        if (nota != null) {
            notaOriginalVacia = nota!!.tituloNota.isEmpty() && nota!!.contenidoNota.isEmpty()
            binding.etTitulo.setText(nota!!.tituloNota)
            binding.etContenido.setText(nota!!.contenidoNota)
        } else {
            finish()
            return
        }

        binding.btnGuardar.setOnClickListener {
            guardarNota()
            Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnEliminar.setOnClickListener {
            nota?.let {
                viewModel.eliminarNota(it.id)
                Toast.makeText(this, "Nota eliminada", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun guardarNota() {
        val titulo = binding.etTitulo.text.toString().trim()
        val contenido = binding.etContenido.text.toString().trim()

        nota?.let {
            if (titulo.isNotEmpty() || contenido.isNotEmpty()) {
                it.tituloNota = titulo
                it.contenidoNota = contenido
                viewModel.actualizarNota(it)
            } else if (notaOriginalVacia) {
                viewModel.eliminarNota(it.id)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        guardarNota()
    }
}
