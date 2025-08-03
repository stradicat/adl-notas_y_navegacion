package dev.dmayr.notasynavegacion

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.dmayr.notasynavegacion.adapter.NotasAdapter
import dev.dmayr.notasynavegacion.databinding.ActivityMainBinding
import dev.dmayr.notasynavegacion.model.Nota
import dev.dmayr.notasynavegacion.viewmodel.NotasViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NotasViewModel
    private lateinit var adapter: NotasAdapter

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

        binding.rvNotas.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.notas.observe(this) { notas ->
            adapter.enviarLista(notas)
        }

        binding.btnAgregarNota.setOnClickListener {
            lifecycleScope.launch {
                val nuevaNota = Nota(tituloNota = "", contenidoNota = "")
                val newId = viewModel.agregarNota(nuevaNota)

                val intent = Intent(this@MainActivity, DetalleNotaActivity::class.java)
                intent.putExtra("nota_id", newId)
                startActivity(intent)
            }
        }

        binding.btnBorrarTodasLasTareas.setOnClickListener {
            lifecycleScope.launch {
                viewModel.eliminarTodas()
                Toast.makeText(
                    this@MainActivity,
                    "Todas las notas han sido eliminadas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.toolbarBusqueda.buscaNotas.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.buscarNotas(query ?: "")

                binding.toolbarBusqueda.buscaNotas.clearFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.toolbarBusqueda.buscaNotas.windowToken, 0)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.buscarNotas(newText ?: "")
                return true
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
