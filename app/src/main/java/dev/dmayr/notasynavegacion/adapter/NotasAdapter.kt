package dev.dmayr.notasynavegacion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.dmayr.notasynavegacion.databinding.ItemNotaBinding
import dev.dmayr.notasynavegacion.model.Nota
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotasAdapter(
    private val onNotaClick: (Nota) -> Unit
) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    private lateinit var binding: ItemNotaBinding
    private var notas = listOf<Nota>()

    fun enviarLista(nuevaLista: List<Nota>) {
        notas = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotaViewHolder {
        binding = ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NotaViewHolder,
        position: Int
    ) {
        holder.bind(notas[position])
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    inner class NotaViewHolder(binding: ItemNotaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nota: Nota) {
            val tvTitulo = binding.tvTitulo // itemView.
            val tvFecha = binding.tvFecha // itemView.

            tvTitulo.text = nota.tituloNota.ifEmpty { "Sin título" }
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            tvFecha.text = dateFormat.format(Date(nota.fechaCreacion))

            itemView.setOnClickListener { onNotaClick(nota) }
        }
    }
}
