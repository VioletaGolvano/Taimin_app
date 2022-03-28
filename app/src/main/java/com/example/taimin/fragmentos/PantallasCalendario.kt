package com.example.taimin.fragmentos

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.clases.Evento
import com.example.taimin.databinding.FragmentPantallasCalendarioBinding
import me.jlurena.revolvingweekview.WeekViewEvent

import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekView.*
import org.threeten.bp.DayOfWeek
import java.time.LocalDate


class PantallasCalendario : Fragment() {
    lateinit var binding: FragmentPantallasCalendarioBinding
    lateinit var eventosUsuario: List<Evento>
    lateinit var momentoSeleccionado: LocalDate
    companion object {
        fun newInstance(): PantallasCalendario = PantallasCalendario()
    }

    private var listenerToday = View.OnClickListener{ today() }
    private var listenerThisWeek = View.OnClickListener{ thisWeek() }
    private var listenerThisMonth = View.OnClickListener{ thisMonth() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pantallas_calendario, container, false)
        (activity as MainActivity).fragmentoActual(this)

        eventosUsuario = (activity as MainActivity).usuario!!.getEventos()

        momentoSeleccionado = LocalDate.now()
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

    fun today(){
        comun()
        binding.titulo.text = resources.getText(R.string.today)
        // Colores botones de cambio pantallas
        binding.calendarioDiario.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        var calendarioDiario = binding.calendarioDiario

        val hoy = LocalDate.of(momentoSeleccionado.year, momentoSeleccionado.month, momentoSeleccionado.dayOfMonth)
        var dia = eventosUsuario.filter { it -> it.fecha==hoy}.map { it -> it.evento }

        calendarioDiario.weekViewLoader = WeekViewLoader { // Add some events
            dia
        }
        calendarioDiario.setOnEventClickListener { event, eventRect ->
            var ev = eventosUsuario.filter { it -> event.identifier == it.evento.identifier }.first()
            Navigation.findNavController((activity as MainActivity), R.id.nav_host_fragment).navigate(
                PantallasCalendarioDirections.actionPantallasCalendarioToVerElemento(ev.idElemento.toString()))
        }

    }
    fun thisWeek(){
        comun()
        binding.titulo.text = resources.getText(R.string.this_week)
        // Colores botones de cambio pantallas
        binding.calendarioSemanal.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)

        binding.calendarioSemanal.visibility = VISIBLE

        var calendarioSemanal = binding.calendarioSemanal

        val ahora = LocalDate.now()
        val hoy = LocalDate.of(ahora.year, ahora.month, ahora.dayOfMonth)
        val lunes = hoy.minusDays((hoy.dayOfWeek.value).toLong())
        var semana = eventosUsuario.filter { it ->
            (lunes < it.fecha) && (it.fecha!! <= lunes.plusDays((7).toLong())) }.map { it -> it.evento }

        calendarioSemanal.weekViewLoader = WeekViewLoader { // Add some events
            semana
        }
        calendarioSemanal.setOnEventClickListener { event, eventRect ->
            var ev = eventosUsuario.filter { it -> event.identifier == it.evento.identifier }.first()
            Navigation.findNavController((activity as MainActivity), R.id.nav_host_fragment).navigate(
                PantallasCalendarioDirections.actionPantallasCalendarioToVerElemento(ev.idElemento.toString()))
        }
        calendarioSemanal.firstDayOfWeek = DayOfWeek.MONDAY
    }
    fun thisMonth(){
        comun()
        binding.titulo.text = resources.getText(R.string.this_month)
        // Colores botones de cambio pantallas
        binding.calendarioMensual.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)

    }

}