package com.example.taimin.fragmentos

import android.os.Bundle
import android.view.*
import android.widget.CalendarView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.TaiminApplication
import com.example.taimin.clases.Evento
import com.example.taimin.clases.Repeticion
import com.example.taimin.clases.Usuario
import com.example.taimin.clases.elementos.*
import com.example.taimin.databinding.FragmentPantallasCalendarioBinding
import kotlinx.android.synthetic.main.fragment_pantallas_calendario.*
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekViewEvent

import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekView.*
import org.threeten.bp.DayOfWeek
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class PantallasCalendario : Fragment() {
    lateinit var binding: FragmentPantallasCalendarioBinding
    lateinit var eventosUsuario: List<Evento>
    lateinit var elementosRepetidos: List<Elemento>
    lateinit var momentoSeleccionado: LocalDate
    var eventosSemana = mutableListOf<WeekViewEvent>()
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
        elementosRepetidos = (activity as MainActivity).usuario?.elementos?.filter { elemento ->
            when(elemento.getIDClase()){
                1, 2, 3 -> (elemento as ElementoCreable).getRepeticion()!=null && !elemento.isCompleted() &&
                        elemento.getContenedor()!=(activity as MainActivity).usuario!!.getArchived()
                else -> false
            }
        } ?: emptyList()


        momentoSeleccionado = ((activity as MainActivity).application as TaiminApplication).momentoSeleccionado
        comun()
        eventosRepeticion()
        today()

        binding.calendarioMensual.setOnDateChangeListener { _: CalendarView, anio: Int, mes: Int, dia: Int ->
            ((activity as MainActivity).application as TaiminApplication).momentoSeleccionado = LocalDate.of(anio, mes+1, dia)
            eventosRepeticion()

            Navigation.findNavController((activity as MainActivity), R.id.nav_host_fragment).navigate(
                PantallasCalendarioDirections.actionPantallasCalendarioSelf())
        }

        return binding.root
    }
    private fun eventosRepeticion(){
        eventosSemana = mutableListOf()
        val idsRepetidos = elementosRepetidos.map { it -> it.getId() }

        val lunes = momentoSeleccionado.minusDays((momentoSeleccionado.dayOfWeek.value).toLong())
        eventosSemana.addAll(eventosUsuario.filter { it ->
            (((lunes < it.fecha) && (it.fecha!! <= lunes.plusDays((7).toLong()))) && !idsRepetidos.contains(it.idElemento))}
            .map { it -> it.evento })

        var diaSemana = DayOfWeek.valueOf(momentoSeleccionado.dayOfWeek.toString())
        var ini: DayTime
        var fin: DayTime
        var allDay: Boolean

        elementosRepetidos.filter { elemento ->
            if ((elemento as ElementoCreable).getFechaFin()!! > momentoSeleccionado) {
                false
            } else {
                var ev: WeekViewEvent
                if (elemento is Tarea){
                    ini = DayTime(diaSemana,elemento.getHoraIni()?.hour?:9,elemento.getHoraIni()?.minute?:0)
                    fin = DayTime(diaSemana,elemento.getHoraFin()?.hour?:10,elemento.getHoraFin()?.minute?:0)
                    allDay = elemento.getHoraIni()==null && elemento.getHoraFin()==null
                } else{
                    ini = DayTime(diaSemana,9,0)
                    fin = DayTime(diaSemana,10,0)
                    allDay = true
                }
                when((elemento as ElementoCreable).getRepeticion()){
                    Repeticion.DAILY ->{
                        DayOfWeek.values().forEach{
                            var inicio = DayTime(it, ini.hour, ini.minute)
                            var final = DayTime(it, fin.hour, fin.minute)
                            ev = WeekViewEvent(elemento.getId().toString(),elemento.getTitulo(), it.toString(), inicio, final, allDay)
                            ev.color = elemento.getColor()!!
                            eventosSemana.add(ev)
                        }
                        return@filter true
                    }
                    Repeticion.WEEKLY -> {
                        diaSemana = DayOfWeek.valueOf(elemento.getFechaFin()!!.dayOfWeek.toString())
                        var inicio = DayTime(diaSemana, ini.hour, ini.minute)
                        var final = DayTime(diaSemana, fin.hour, fin.minute)
                        ev = WeekViewEvent(elemento.getId().toString(),elemento.getTitulo(), diaSemana.toString(), inicio, final, allDay)
                        ev.color = elemento.getColor()!!
                        eventosSemana.add(ev)
                        return@filter true
                    }
                    Repeticion.MONTHLY -> {
                        var mes = LocalDate.of(momentoSeleccionado.year, momentoSeleccionado.month, 1)
                        if (elemento.getFechaFin()!!.dayOfMonth < mes.lengthOfMonth()) {
                            var fecha = LocalDate.of(
                                momentoSeleccionado.year,
                                momentoSeleccionado.month,
                                elemento.getFechaFin()!!.dayOfMonth
                            )
                            diaSemana = DayOfWeek.valueOf(fecha.dayOfWeek.toString())
                            if ((lunes < fecha) && (fecha!! <= lunes.plusDays((7).toLong()))) {
                                var inicio = DayTime(diaSemana, ini.hour, ini.minute)
                                var final = DayTime(diaSemana, fin.hour, fin.minute)
                                ev = WeekViewEvent(
                                    elemento.getId().toString(),
                                    elemento.getTitulo(),
                                    diaSemana.toString(),
                                    inicio,
                                    final,
                                    allDay
                                )
                                ev.color = elemento.getColor()!!
                                eventosSemana.add(ev)
                                return@filter true
                            }
                        }
                    }
                    Repeticion.YEARLY -> {
                        var mes = LocalDate.of(momentoSeleccionado.year, momentoSeleccionado.month, 1)
                        if (elemento.getFechaFin()!!.dayOfMonth>mes.lengthOfMonth()){
                            var fecha = LocalDate.of(momentoSeleccionado.year, elemento.getFechaFin()!!.month, elemento.getFechaFin()!!.dayOfMonth)
                            diaSemana = DayOfWeek.valueOf(fecha.dayOfWeek.toString())
                            if ((lunes < fecha) && (fecha!! <= lunes.plusDays((7).toLong()))){
                                var inicio = DayTime(diaSemana, ini.hour, ini.minute)
                                var final = DayTime(diaSemana, fin.hour, fin.minute)
                                ev = WeekViewEvent(elemento.getId().toString(),elemento.getTitulo(), diaSemana.toString(), inicio, final, allDay)
                                ev.color = elemento.getColor()!!
                                eventosSemana.add(ev)
                                return@filter true
                            }
                        }
                    }
                }
                false
            }
        }
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

        binding.calendarioDiario.weekViewLoader = WeekViewLoader { eventosSemana }
        binding.calendarioDiario.goToNow()
        binding.calendarioDiario.goToDay(momentoSeleccionado.dayOfWeek.value)

        binding.calendarioDiario.setOnEventClickListener { event, _ ->
            var evento = eventosUsuario.filter { it -> event.identifier == it.idElemento.toString() ||
                    event.identifier == it.id.toString()}.first()
            Navigation.findNavController((activity as MainActivity), R.id.nav_host_fragment).navigate(
                PantallasCalendarioDirections.actionPantallasCalendarioToVerElemento(evento.idElemento.toString()))
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

        binding.calendarioSemanal.weekViewLoader = WeekViewLoader { eventosSemana }
        binding.calendarioDiario.goToNow()
        binding.calendarioSemanal.dayIsValid(DayOfWeek.valueOf(momentoSeleccionado.dayOfWeek.toString()))

        binding.calendarioSemanal.setOnEventClickListener { event, _ ->
            var evento = eventosUsuario.first { event.identifier == it.idElemento.toString() || event.identifier == it.id.toString() }
            Navigation.findNavController((activity as MainActivity), R.id.nav_host_fragment).navigate(
                PantallasCalendarioDirections.actionPantallasCalendarioToVerElemento(evento.idElemento.toString()))
        }
        binding.calendarioSemanal.firstDayOfWeek = DayOfWeek.MONDAY
    }
    fun thisMonth(){
        comun()
        binding.titulo.text = resources.getText(R.string.this_month)
        // Colores botones de cambio pantallas
        binding.calendarioMensual.visibility = VISIBLE
        binding.primeraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.segundaPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
        binding.terceraPantalla.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN)

        binding.calendarioMensual.firstDayOfWeek = Calendar.MONDAY
        var calendar = Calendar.getInstance()
        calendar.set(momentoSeleccionado.year,momentoSeleccionado.monthValue-1,momentoSeleccionado.dayOfMonth)
        binding.calendarioMensual.date = calendar.timeInMillis
    }

}