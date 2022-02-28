package com.example.taimin.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentVerElementoBinding
import elementos.ElementoCreable
import elementos.Tarea
import java.time.LocalDate
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VerElemento.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerElemento : Fragment() {
    lateinit var binding: FragmentVerElementoBinding
    lateinit var elemento: ElementoCreable
    companion object {
        fun newInstance(): VerElemento = VerElemento()
    }
    fun iniciar() {
        elemento = Tarea((activity as MainActivity).usuario)
        elemento.setTitulo("Tarea 1")
        elemento.setDescripcion("Esta es la descripción de la tarea, que no debería mostrar más de cuatro líneas")
        (activity as MainActivity).usuario.getDaily().addContenido(elemento)
        elemento.setFechaIni(LocalDate.of(2020, 2, 2))
        elemento.setFechaFin(LocalDate.of(2020, 2, 3))
        (elemento as Tarea).setHoraIni(LocalDate.now())
        (elemento as Tarea).setHoraFin(LocalDate.now())
        elemento.setRepeticion(Repeticion.WEEKLY)
        elemento.setPrioridad(Prioridad.ALTA)
        elemento.aceptar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ver_elemento, container, false)

        val args = VerElementoArgs.fromBundle(requireArguments())
        elemento = (activity as MainActivity).usuario.buscar(UUID.fromString(args.elementoId)) as ElementoCreable
            //CardsApplication.getCard(args.cardId) ?: throw Exception("Wrong id")
        //iniciar()
        binding.elemento = elemento

        return binding.root
    }

}