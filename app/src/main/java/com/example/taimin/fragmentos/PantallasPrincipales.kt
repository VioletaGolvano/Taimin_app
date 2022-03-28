package com.example.taimin.fragmentos

import android.accounts.AccountManager.get
import android.app.Application
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ViewConfiguration.get
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeViewModelStoreOwner.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.TaiminApplication
import com.example.taimin.database.ElementosListViewModel
import com.example.taimin.databinding.FragmentPantallasPrincipalesBinding
import kotlinx.android.synthetic.main.fragment_add_elemento.view.*
import kotlinx.android.synthetic.main.fragment_pantallas_principales.*

class PantallasPrincipales : Fragment() {
    lateinit var binding: FragmentPantallasPrincipalesBinding
    private lateinit var adapter: PantallasAdapter
    private val elementosListViewModel by lazy {
        ViewModelProvider(this).get(ElementosListViewModel::class.java)
    }
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

        adapter.data = if (binding.pantalla==null) emptyList()
            else binding.pantalla!!.contenidos


        // fecha límite
        // prioridad
        // progreso
        //binding.pantalla!!.contenidos
            /*.sortedWith(compareBy({
            (it as ElementoCreable).getFechaFin() }, {
            (it as ElementoCreable).getPrioridad() }, {
            (it as ElementoCreable).getProgreso() }))
             */

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