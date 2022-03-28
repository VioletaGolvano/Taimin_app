package com.example.taimin.fragmentos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.taimin.R
import com.example.taimin.clases.Prioridad
import com.example.taimin.clases.elementos.Elemento
import com.example.taimin.clases.elementos.ElementoCreable
import java.lang.Exception

class PantallasAdapter : RecyclerView.Adapter<PantallasAdapter.PantallasHolder>() {
    /*
    var deckId: String? = null
    lateinit var binding: ListItemCardBinding
     */
    var data = listOf<Elemento>()

    inner class PantallasHolder(view: View) : RecyclerView.ViewHolder(view){
        var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        var titulo: TextView = itemView.findViewById(R.id.titulo)
        var item: RelativeLayout = itemView.findViewById(R.id.item)
        var prioridad: RelativeLayout = itemView.findViewById(R.id.line_priority)
        lateinit var elemento: Elemento

        fun bind(elemento: Elemento) {
            this.elemento = elemento
            titulo.text = elemento.getTitulo()

            val color = (elemento as ElementoCreable).getColor()
            if (color != null){
                item.setBackgroundColor(color)
            }

            prioridad.setBackgroundResource(
                when (elemento.getPrioridad()){
                    Prioridad.ALTA -> R.color.red
                    Prioridad.MEDIA -> R.color.ambar
                    Prioridad.BAJA -> R.color.blue_prio
                    Prioridad.NULA -> R.color.grey
                })

            itemView.setOnClickListener {
                try {
                    Navigation.findNavController(it).navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToVerElemento(elemento.getId().toString()))
                }catch (e: Exception){
                    Navigation.findNavController(it).navigate(PantallasArchivoDirections.actionPantallasArchivoToVerElemento(elemento.getId().toString()))
                }
            }

            if (elemento.isCompleted()){
                checkBox.isChecked = true
                itemView.alpha = 0.5F
            }

            checkBox.setOnClickListener{
                if (elemento.isCompleted()){
                    elemento.setProgreso(0.0)
                    itemView.alpha = 1F
                }else{
                    elemento.completado()
                }
                notifyDataSetChanged()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PantallasHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_elemento, parent, false)
        return PantallasHolder(view)
    }

    override fun getItemCount() = data.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: PantallasHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
