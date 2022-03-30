package com.example.taimin.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.clases.Prioridad
import com.example.taimin.databinding.FragmentVerElementoBinding
import com.example.taimin.clases.elementos.ElementoCreable
import com.example.taimin.clases.elementos.Tarea
import com.example.taimin.database.TaiminDatabase
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors


class VerElemento : Fragment() {
    lateinit var binding: FragmentVerElementoBinding
    lateinit var elemento: ElementoCreable
    private lateinit var adapter: ContenidosAdapter
    companion object {
        fun newInstance(): VerElemento = VerElemento()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ver_elemento, container, false)

        (activity as MainActivity).fragmentoActual(this)

        val args = VerElementoArgs.fromBundle(requireArguments())
        val elem = (activity as MainActivity).usuario!!.buscar(UUID.fromString(args.elementoId))
            //CardsApplication.getCard(args.cardId) ?: throw Exception("Wrong id")
        if (elem == null) {
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment).navigateUp()
        } else {
            elemento = elem as ElementoCreable
            binding.elemento = elemento
            elemento.getColor().let { it?.let { it1 -> binding.titulo.setBackgroundColor(it1) } }
            binding.verRepeticiones.text = elemento.getRepeticion()
                ?.let { getString(it?.resource()) }
            binding.edit.setOnClickListener{
                Navigation.findNavController(it).navigate(VerElementoDirections.actionVerElementoToAddElemento(elemento.getId().toString(), elemento.getIDClase()))
            }
            binding.prioridad.setImageDrawable(when (elemento.getPrioridad()){
                Prioridad.ALTA -> resources.getDrawable(R.drawable.ic_baseline_warning_red_24, activity?.theme)
                Prioridad.MEDIA -> resources.getDrawable(R.drawable.ic_baseline_warning_amber_24, activity?.theme)
                Prioridad.BAJA -> resources.getDrawable(R.drawable.ic_baseline_warning_blue_24, activity?.theme)
                else -> resources.getDrawable(R.drawable.ic_baseline_warning_grey_24, activity?.theme)
            })

            binding.fechaFin.text = elemento.getFechaFin()?.format(DateTimeFormatter.ofPattern("EEE, dd LLL"))
            if (elemento is Tarea){
                binding.horaIni.text = (elemento as Tarea).getHoraIni()?.format(DateTimeFormatter.ofPattern("hh:mm"))
                binding.horaFin.text = (elemento as Tarea).getHoraFin()?.format(DateTimeFormatter.ofPattern("hh:mm"))
            }
                //textoHoraFin.text = date.format(DateTimeFormatter.ofPattern("hh:mm"))

            adapter = ContenidosAdapter()

            adapter.data = elemento.contenidos?: emptyList()
            binding.listaContenidos?.adapter = adapter

            binding.back.setOnClickListener {
                Navigation.findNavController(it).navigateUp()
            }
            binding.delete.setOnClickListener {
                elemento.eliminar()
                Executors.newSingleThreadExecutor().execute {
                    context?.let { it1 -> TaiminDatabase.getInstance(context = it1).taiminDAO.delElemento(elemento) }
                }
                Navigation.findNavController(it).navigateUp()
            }


        }
        return binding.root
    }

}