package com.example.taimin.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentPantallasArchivoBinding

class PantallasArchivo : Fragment() {
    lateinit var binding: FragmentPantallasArchivoBinding
    private lateinit var adapter: PantallasAdapter
    companion object {
        fun newInstance(): PantallasArchivo = PantallasArchivo()
    }
    private var listenerArchived = View.OnClickListener{ archived() }
    private var listenerCompleted = View.OnClickListener{ completed() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_archivo, container, false)
        archived()
        (activity as MainActivity).fragmentoActual(this)

        binding.archivedPantalla.setOnClickListener(listenerArchived)
        binding.completedPantalla.setOnClickListener(listenerCompleted)
        return binding.root
    }
    public fun archived(){
        binding.pantalla = (activity as MainActivity).usuario.getArchived()
        // Colores botones de cambio pantallas
        (binding.archivedPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.completedPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        adapter = PantallasAdapter()

        adapter.data = binding.pantalla!!.contenidos
        binding.listaElementos?.adapter = adapter
    }
    public fun completed(){
        binding.pantalla = (activity as MainActivity).usuario.getCompleted()
        // Colores botones de cambio pantallas
        (binding.archivedPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        (binding.completedPantalla as ImageView).setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)

        adapter = PantallasAdapter()

        adapter.data = (activity as MainActivity).usuario.getElementosCompletados()
        binding.listaElementos?.adapter = adapter
    }
}