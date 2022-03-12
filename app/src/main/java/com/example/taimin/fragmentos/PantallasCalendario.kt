package com.example.taimin.fragmentos

import Evento
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentPantallasCalendarioBinding
import me.jlurena.revolvingweekview.WeekViewEvent

import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekView.*
import org.threeten.bp.DayOfWeek
import java.time.LocalDate


class PantallasCalendario : Fragment() {
    lateinit var binding: FragmentPantallasCalendarioBinding
    lateinit var eventosUsuario: List<Evento>
    companion object {
        fun newInstance(): PantallasCalendario = PantallasCalendario()
    }

    private var listenerToday = View.OnClickListener{ today() }
    private var listenerThisWeek = View.OnClickListener{ thisWeek() }
    private var listenerThisMonth = View.OnClickListener{ thisMonth() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_calendario, container, false)
        (activity as MainActivity).fragmentoActual(this)

        eventosUsuario = (activity as MainActivity).usuario.getEventos()

        comun()
        today()
        return binding.root
    }
    private fun comun(){
        binding.primeraPantalla.setOnClickListener(listenerToday)
        binding.segundaPantalla.setOnClickListener(listenerThisWeek)
        binding.terceraPantalla.setOnClickListener(listenerThisMonth)

        binding.calendarioDiario.visibility = GONE
        binding.calendarioSemanal.visibility = GONE
        binding.calendarioMensual.visibility = GONE
    }

    public fun today(){
        comun()
        // título
        // Colores botones de cambio pantallas
        binding.calendarioDiario.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        var calendarioDiario = binding.calendarioDiario

        calendarioDiario.weekViewLoader = WeekViewLoader { // Add some events
            val ahora = LocalDate.now()
            val hoy = LocalDate.of(ahora.year, ahora.month, ahora.dayOfMonth)

            eventosUsuario.filter { it -> it.fecha==hoy}.map { it -> it.evento }
        }

    }
    public fun thisWeek(){
        comun()
        // titulo
        // Colores botones de cambio pantallas
        binding.calendarioSemanal.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        binding.calendarioSemanal.visibility = VISIBLE

        var calendarioSemanal = binding.calendarioSemanal

        calendarioSemanal.weekViewLoader = WeekViewLoader { // Add some events
            val ahora = LocalDate.now()
            val hoy = LocalDate.of(ahora.year, ahora.month, ahora.dayOfMonth)
            val lunes = hoy.minusDays((hoy.dayOfWeek.value).toLong())

            eventosUsuario.filter { it ->
                (lunes < it.fecha) && (it.fecha!! <= lunes.plusDays((7).toLong())) }.map { it -> it.evento }
        }
        calendarioSemanal.firstDayOfWeek = DayOfWeek.MONDAY
    }
    public fun thisMonth(){
        comun()
        // titulo
        // Colores botones de cambio pantallas
        binding.calendarioMensual.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)

    }

}