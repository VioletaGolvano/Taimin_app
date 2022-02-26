package com.example.taimin.fragmentos

import OnSwipeTouchListener
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_principales, container, false)
        default()

        /* binding.cardListStudyButton.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_cardListFragment_to_studyFragment)}*/

        return binding.root
    }
    public fun default(){
        binding.pantalla = (activity as MainActivity).usuario.getDefault()

        adapter = PantallasAdapter()

        adapter.data = (activity as MainActivity).usuario.getDefault().contenidos
        binding.listaElementos?.adapter = adapter

    }
    public fun daily(){
        binding.pantalla = (activity as MainActivity).usuario.getDaily()

        adapter = PantallasAdapter()

        adapter.data = (activity as MainActivity).usuario.getDaily().contenidos
        binding.listaElementos?.adapter = adapter
    }

    public fun toDo(){
        binding.pantalla = (activity as MainActivity).usuario.getToDo()

        adapter = PantallasAdapter()

        adapter.data = (activity as MainActivity).usuario.getToDo().contenidos
        binding.listaElementos?.adapter = adapter
    }
    /*
    background.setOnTouchListener(new OnSwipeTouchListener() {
        public boolean onSwipeTop() {
            Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onSwipeRight() {
            Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onSwipeLeft() {
            Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onSwipeBottom() {
            Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            return true;
        }
    });

     */
}