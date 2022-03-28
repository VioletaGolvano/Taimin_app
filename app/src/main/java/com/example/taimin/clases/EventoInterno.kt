package com.example.taimin.clases
import java.util.*
import androidx.room.Entity

@Entity(tableName = "tabla_eventoInterno")
class EventoInterno : Evento() {
    lateinit var hora: java.time.LocalTime
    fun duplicar(): EventoInterno {
        var copia = EventoInterno()
        copia.hora = this.hora
        return copia
    }

}
