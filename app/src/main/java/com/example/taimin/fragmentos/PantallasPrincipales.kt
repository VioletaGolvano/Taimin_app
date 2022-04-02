package com.example.taimin.fragmentos

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.TaiminApplication
import com.example.taimin.clases.Prioridad
import com.example.taimin.clases.elementos.*
import com.example.taimin.databinding.FragmentPantallasPrincipalesBinding
import java.time.LocalDate

class PantallasPrincipales : Fragment() {
    lateinit var binding: FragmentPantallasPrincipalesBinding
    private lateinit var adapter: PantallasAdapter
    companion object {
        fun newInstance(): PantallasPrincipales = PantallasPrincipales()
    }
    private var listenerDaily = View.OnClickListener{ daily() }
    private var listenerDefault = View.OnClickListener{ defaultpp() }
    private var listenerToDo = View.OnClickListener{ toDo() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_principales, container, false)
        (activity as MainActivity).fragmentoActual(this)
        // Esto es una guarrada
        (activity as MainActivity).usuario = ((activity as MainActivity).application as TaiminApplication).usuario

        defaultpp()
        return binding.root
    }
    private fun comun(){
        adapter = PantallasAdapter()

        binding.primeraPantalla.setOnClickListener(listenerDaily)
        binding.segundaPantalla.setOnClickListener(listenerDefault)
        binding.terceraPantalla.setOnClickListener(listenerToDo)

        val comparator = compareBy<Elemento> { it.isCompleted() }
            .thenComparing(compareBy<Elemento, LocalDate?>(nullsLast(), { (it as ElementoCreable).getFechaFin()})
            .thenByDescending { (it as ElementoCreable).getPrioridad().ordinal }
            .thenBy {(it as ElementoCreable).getProgreso()})

        adapter.data = if (binding.pantalla==null) emptyList()
            else binding.pantalla!!.contenidos.sortedWith(comparator)

        binding.listaElementos?.adapter = adapter
    }

    public fun defaultpp(){
        binding.pantalla = (activity as MainActivity).usuario?.getDefault()
        // Colores botones de cambio pantallas
        (binding.primeraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.segundaPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.terceraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        comun()
    }
    public fun daily(){
        binding.pantalla = (activity as MainActivity).usuario?.getDaily()
        // Colores botones de cambio pantallas
        (binding.primeraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.segundaPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.terceraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        comun()
    }
    public fun toDo(){
        binding.pantalla = (activity as MainActivity).usuario?.getToDo()
        // Colores botones de cambio pantallas
        (binding.primeraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.segundaPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.terceraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)

        comun()
    }
}