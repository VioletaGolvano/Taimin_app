package com.example.taimin.fragmentos

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.databinding.FragmentPantallasCalendarioBinding
import me.jlurena.revolvingweekview.WeekViewEvent

import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekView.WeekViewLoader
import org.threeten.bp.DayOfWeek
import java.time.LocalDate


class PantallasCalendario : Fragment() {
    lateinit var binding: FragmentPantallasCalendarioBinding
    companion object {
        fun newInstance(): PantallasCalendario = PantallasCalendario()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_calendario, container, false)
        (activity as MainActivity).fragmentoActual(this)

        // Get a reference for the week view in the layout.
        // Get a reference for the week view in the layout.
        var calendarioSemanal = binding.calendarioSemanal

        // Set an WeekViewLoader to draw the events on load.

        // Set an WeekViewLoader to draw the events on load.
        calendarioSemanal.weekViewLoader = WeekViewLoader { // Add some events
            val ahora = LocalDate.now()
            val hoy = LocalDate.of(ahora.year, ahora.month, ahora.dayOfMonth)
            val lunes = hoy.minusDays((hoy.dayOfWeek.value).toLong())

            (activity as MainActivity).usuario.getEventos().filter { it ->
                (lunes < it.fecha) && (it.fecha!! <= lunes.plusDays((7).toLong())) }.map { it -> it.evento }
        }
        calendarioSemanal.firstDayOfWeek = DayOfWeek.MONDAY
        // There are many other Listeners to choose from as well.

        return binding.root
    }

}