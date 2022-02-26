package com.example.taimin.fragmentos

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taimin.R
import elementos.Elemento
import elementos.ElementoCreable

class PantallasAdapter : RecyclerView.Adapter<PantallasAdapter.PantallasHolder>() {
    private var holderCounter = 0
    /*
    var deckId: String? = null
    lateinit var binding: ListItemCardBinding
     */
    var data = listOf<Elemento>()

    class PantallasHolder(view: View) : RecyclerView.ViewHolder(view){
        var titulo: CheckBox = itemView.findViewById(R.id.checkbox)
        var item: RelativeLayout = itemView.findViewById(R.id.item)
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
        holder.apply {
            titulo.text = data[position].getTitulo()
            var color = (data[position] as ElementoCreable).getColorElemento()
            if (color != null){
                item.background = ColorDrawable(color)
            }
        }
    }


/*
    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view){
        lateinit var card: Card

        fun bind(card: Card) {
            this.card = card
            binding.card = card
        }

        init {
            binding.listItemQuestion.setOnClickListener {
                it.findNavController()
                    .navigate(CardListFragmentDirections
                        .actionCardListFragmentToCardEditFragment(card.id, deckId))
            }
            binding.listItemQuestion.setOnLongClickListener {
                Toast.makeText(it.context, card.details(), Toast.LENGTH_LONG).show()
                true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemCardBinding.inflate(layoutInflater, parent, false)
        return CardHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
 */
}
