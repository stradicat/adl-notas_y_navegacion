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

    private var notas = listOf<Nota>()

    fun enviarLista(nuevaLista: List<Nota>) {
        notas = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotaViewHolder {
        val itemBinding =
            ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(itemBinding)
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

    inner class NotaViewHolder(private val binding: ItemNotaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nota: Nota) {
            itemView.apply {
                binding.tvTitulo.text =
                    nota.tituloNota.ifEmpty { "Sin título" }

                val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                binding.tvFecha.text = formatoFecha.format(Date(nota.fechaCreacion))

                setOnClickListener {
                    onNotaClick(nota)
                }
            }
        }
    }
}
