package com.example.taimin.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import androidx.navigation.Navigation
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentVerElementoBinding
import elementos.ElementoCreable
import elementos.Tarea
import java.time.LocalDate
import java.util.*


class VerElemento : Fragment() {
    lateinit var binding: FragmentVerElementoBinding
    lateinit var elemento: ElementoCreable
    companion object {
        fun newInstance(): VerElemento = VerElemento()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ver_elemento, container, false)

        val args = VerElementoArgs.fromBundle(requireArguments())
        val elem = (activity as MainActivity).usuario.buscar(UUID.fromString(args.elementoId))
            //CardsApplication.getCard(args.cardId) ?: throw Exception("Wrong id")
        if (elem == null) {
            when (Navigation.findNavController(
                activity as MainActivity,
                R.id.nav_host_fragment
            ).previousBackStackEntry?.destination?.id) {
                R.id.pantallasCalendario -> (activity as MainActivity).bottomBarCalendario()
                R.id.pantallasArchivo -> (activity as MainActivity).bottomBarArchivo()
                else -> (activity as MainActivity).bottomBarPP()
            }
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
                .navigateUp()
        } else {
            elemento = elem as ElementoCreable
            binding.elemento = elemento
            elemento.getColorElemento().let { it?.let { it1 -> binding.titulo.setBackgroundColor(it1) } }
            binding.edit.setOnClickListener{
                (it.context as MainActivity).bottomBarAddElement()
                Navigation.findNavController(it).navigate(VerElementoDirections.actionVerElementoToAddElemento(elemento.getID().toString(), elemento.getIDClase()))
            }
            binding.prioridad.setImageDrawable(when (elemento.getPrioridad()){
                Prioridad.ALTA -> resources.getDrawable(R.drawable.ic_baseline_warning_red_24, activity?.theme)
                Prioridad.MEDIA -> resources.getDrawable(R.drawable.ic_baseline_warning_amber_24, activity?.theme)
                Prioridad.BAJA -> resources.getDrawable(R.drawable.ic_baseline_warning_blue_24, activity?.theme)
                else -> resources.getDrawable(R.drawable.ic_baseline_warning_grey_24, activity?.theme)
            })

            binding.back.setOnClickListener {
                when(Navigation.findNavController(it).previousBackStackEntry?.destination?.id){
                    R.id.pantallasCalendario -> (it.context as MainActivity).bottomBarCalendario()
                    R.id.pantallasArchivo -> (it.context as MainActivity).bottomBarArchivo()
                    else -> (it.context as MainActivity).bottomBarPP()
                }
                Navigation.findNavController(it).navigateUp()
            }
            binding.delete.setOnClickListener {
                elemento.eliminar()
                when(Navigation.findNavController(it).previousBackStackEntry?.destination?.id){
                    R.id.pantallasCalendario -> (it.context as MainActivity).bottomBarCalendario()
                    R.id.pantallasArchivo -> (it.context as MainActivity).bottomBarArchivo()
                    else -> (it.context as MainActivity).bottomBarPP()
                }
                Navigation.findNavController(it).navigateUp()
            }
        }
        return binding.root
    }

}