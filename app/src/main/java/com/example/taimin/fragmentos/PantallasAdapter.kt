package com.example.taimin.fragmentos

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.taimin.MainActivity
import com.example.taimin.R
import elementos.Elemento
import elementos.ElementoCreable
import java.lang.Exception

class PantallasAdapter : RecyclerView.Adapter<PantallasAdapter.PantallasHolder>() {
    private var holderCounter = 0
    /*
    var deckId: String? = null
    lateinit var binding: ListItemCardBinding
     */
    var data = listOf<Elemento>()
    var navController: NavController? = null

    inner class PantallasHolder(view: View) : RecyclerView.ViewHolder(view){
        var titulo: TextView = itemView.findViewById(R.id.titulo)
        var item: RelativeLayout = itemView.findViewById(R.id.item)
        lateinit var elemento: Elemento

        fun bind(elemento: Elemento) {
            this.elemento = elemento
            titulo.text = elemento.getTitulo()
            var color = (elemento as ElementoCreable).getColorElemento()
            if (color != null){
                item.setBackgroundColor(color)
            }

            itemView.setOnClickListener {
                try {
                    Navigation.findNavController(it).navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToVerElemento(elemento.getID().toString()))
                }catch (e: Exception){
                    Navigation.findNavController(it).navigate(PantallasArchivoDirections.actionPantallasArchivoToVerElemento(elemento.getID().toString()))
                }
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
