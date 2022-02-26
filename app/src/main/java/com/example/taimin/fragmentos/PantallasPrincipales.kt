package com.example.taimin.fragmentos

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentPantallasPrincipalesBinding
import elementos.*

class PantallasPrincipales : Fragment() {
    lateinit var binding: FragmentPantallasPrincipalesBinding
    private lateinit var adapter: PantallasAdapter
    companion object {
        fun newInstance(): PantallasPrincipales = PantallasPrincipales()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_principales, container, false)
        binding.pantalla = (activity as MainActivity).usuario.getDefault()

        adapter = PantallasAdapter()

        adapter.data = (activity as MainActivity).usuario.getDefault().contenidos
        binding.listaElementos?.adapter = adapter

        /*
        binding.cardListStudyButton.setOnClickListener { view ->
                view.findNavController()
                    .navigate(R.id.action_cardListFragment_to_studyFragment)
        }

         */


        return binding.root
    }
}