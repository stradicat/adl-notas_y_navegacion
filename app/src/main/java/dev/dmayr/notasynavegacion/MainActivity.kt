package dev.dmayr.notasynavegacion

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.dmayr.notasynavegacion.adapter.NotasAdapter
import dev.dmayr.notasynavegacion.databinding.ActivityMainBinding
import dev.dmayr.notasynavegacion.model.Nota
import dev.dmayr.notasynavegacion.viewmodel.NotasViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var viewModel: NotasViewModel
    private lateinit var adapter: NotasAdapter

    private val listaDeNotas: MutableList<Nota> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)

        viewModel = ViewModelProvider(this)[NotasViewModel::class.java]
        adapter = NotasAdapter { nota ->
            val intent = Intent(this, DetalleNotaActivity::class.java)
            intent.putExtra("nota_id", nota.id)
            startActivity(intent)
        }

        binding.rvNotas.adapter = adapter
        binding.rvNotas.layoutManager = LinearLayoutManager(this)

        // Ejemplo: botón para agregar una nueva nota vacía
        binding.btnAgregarNota.setOnClickListener {
            val nuevaNota = Nota(
                id = System.currentTimeMillis(),
                tituloNota = "",
                contenidoNota = ""
            )
            viewModel.agregarNota(nuevaNota)

            val intent = Intent(this, DetalleNotaActivity::class.java)
            intent.putExtra("nota_id", nuevaNota.id)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarNotas()
    }
}
