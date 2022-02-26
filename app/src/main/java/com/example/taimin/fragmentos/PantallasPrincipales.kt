package com.example.taimin.fragmentos

import android.app.Application
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentPantallasPrincipalesBinding
import elementos.*
import kotlinx.android.synthetic.main.fragment_add_elemento.view.*
import kotlinx.android.synthetic.main.fragment_pantallas_principales.*

class PantallasPrincipales : Fragment() {
    lateinit var binding: FragmentPantallasPrincipalesBinding
    private lateinit var adapter: PantallasAdapter
    companion object {
        fun newInstance(): PantallasPrincipales = PantallasPrincipales()
    }
    private var listenerDaily = View.OnClickListener{ daily() }
    private var listenerDefault = View.OnClickListener{ default() }
    private var listenerToDo = View.OnClickListener{ toDo() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_principales, container, false)
        default()

        /* binding.cardListStudyButton.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment)}*/

        return binding.root
    }
    private fun comun(){
        adapter = PantallasAdapter()

        binding.primeraPantalla.setOnClickListener(listenerDaily)
        binding.segundaPantalla.setOnClickListener(listenerDefault)
        binding.terceraPantalla.setOnClickListener(listenerToDo)
    }

    public fun default(){
        binding.pantalla = (activity as MainActivity).usuario.getDefault()
        // Colores botones de cambio pantallas
        (binding.primeraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.segundaPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.terceraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        comun()

        adapter.data = (activity as MainActivity).usuario.getDefault().contenidos
        binding.listaElementos?.adapter = adapter

    }
    public fun daily(){
        binding.pantalla = (activity as MainActivity).usuario.getDaily()
        // Colores botones de cambio pantallas
        (binding.primeraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.segundaPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.terceraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        comun()

        adapter.data = (activity as MainActivity).usuario.getDaily().contenidos
        binding.listaElementos?.adapter = adapter
    }

    public fun toDo(){
        binding.pantalla = (activity as MainActivity).usuario.getToDo()
        // Colores botones de cambio pantallas
        (binding.primeraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.segundaPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.terceraPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)

        comun()

        adapter.data = (activity as MainActivity).usuario.getToDo().contenidos
        binding.listaElementos?.adapter = adapter
    }
}