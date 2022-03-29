package com.example.taimin.database
import androidx.room.TypeConverter
import com.example.taimin.clases.*
import com.example.taimin.clases.elementos.Elemento
import com.example.taimin.clases.elementos.ElementoCreable
import com.example.taimin.clases.elementos.Proyecto
import com.google.gson.Gson
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekViewEvent
import java.time.*
import java.util.*
import com.google.gson.reflect.TypeToken
import org.threeten.bp.DayOfWeek
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun localTimefromTimestamp(value: Long?): LocalTime? {
        return value?.let { LocalTime.ofNanoOfDay(value) }
    }

    @TypeConverter
    fun localTimeToTimestamp(time: LocalTime?): Long? {
        return time?.toNanoOfDay()
    }

    @TypeConverter
    fun usuarioToString(usuario: Usuario): String {
        return usuario.getID().toString()
    }

    @TypeConverter
    fun stringToUsuario(value: String?): Usuario {
        return Usuario(UUID.fromString(value))
    }

    @TypeConverter
    fun elementoToString(elemento: Elemento?): String? {
        if (elemento != null) {
            return elemento.getId().toString()
        }
        return null
    }

    @TypeConverter
    fun stringToElemento(value: String?): Elemento? {
        if (value==null) return null
        return Proyecto(UUID.fromString(value), true)
    }

    @TypeConverter
    fun weekViewEventToString(weekViewEvent: WeekViewEvent?): String {
        var retVal: String?
        //WeekViewEvent(id.toString(), name, diaSemana.toString(), DayTime(diaSemana, horaInicio), DayTime(diaSemana, horaFinal), allDay)
        if (weekViewEvent==null) return ""
        retVal = weekViewEvent.identifier+"|"+weekViewEvent.name+"|"+weekViewEvent.location+"|"+
                weekViewEvent.startTime.hour+"|"+weekViewEvent.startTime.minute+"|"+
                weekViewEvent.endTime.hour+"|"+weekViewEvent.endTime.minute+"|"+
                weekViewEvent.isAllDay


        return retVal
    }

    @TypeConverter
    fun stringToWeekViewEvent(value: String): WeekViewEvent? {
        if(value.isEmpty()) return null
        val arr = value.split("|").toTypedArray()

        var ini = DayTime(DayOfWeek.valueOf(arr[2]),
            org.threeten.bp.LocalTime.of(arr[3].toInt(),arr[4].toInt()))
        var fin = DayTime(DayOfWeek.valueOf(arr[2]),
            org.threeten.bp.LocalTime.of(arr[5].toInt(),arr[6].toInt()))

        return WeekViewEvent(arr[0], arr[1], arr[2], ini, fin, arr[7].toBoolean())
    }

    @TypeConverter
    fun stringToStringList(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun stringListToString(lista: List<String>): String {
        val gson = Gson()
        return gson.toJson(lista)
    }

    @TypeConverter
    fun stringToUUID(value: String): UUID {
        return UUID.fromString(value)
    }

    @TypeConverter
    fun uuidToString(value: UUID): String {
        return value.toString()
    }
}