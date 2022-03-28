package com.example.taimin.clases
import androidx.room.Embedded
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekViewEvent
import org.threeten.bp.DayOfWeek
import org.threeten.bp.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_evento")
open class Evento {
    @PrimaryKey
    var id: UUID = UUID.randomUUID()
    lateinit var idElemento: UUID
    var fecha: LocalDate? = null
    lateinit var evento: WeekViewEvent

    public fun setEvento(idElemento: UUID, name: String, fecha: LocalDate, horaIni: java.time.LocalTime, horaFin: java.time.LocalTime, allDay: Boolean = false){
        this.idElemento = idElemento
        var diaSemana = DayOfWeek.valueOf(fecha.dayOfWeek.toString())
        var horaInicio = org.threeten.bp.LocalTime.of(horaIni.hour, horaIni.minute)
        var horaFinal = org.threeten.bp.LocalTime.of(horaFin.hour, horaFin.minute)
        evento = WeekViewEvent(id.toString(), name, diaSemana.toString(), DayTime(diaSemana, horaInicio), DayTime(diaSemana, horaFinal), allDay)
        this.fecha = fecha
    }

    public fun setEvento(idElemento: UUID, name: String, fecha: LocalDate, horaIni: java.time.LocalTime, duracion: Int){
        setEvento(idElemento, name, fecha, horaIni, horaIni.plusMinutes(duracion.toLong()))
    }

    public fun setEvento(idElemento: UUID, name: String, fecha: LocalDate){
        this.setEvento(idElemento, name, fecha, LocalTime.of(9,0), LocalTime.of(10,0), true)
    }

    @JvmName("getEvento1")
    public fun getEvento(): WeekViewEvent{
        return evento
    }
}
