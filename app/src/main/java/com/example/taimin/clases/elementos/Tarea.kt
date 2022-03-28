package com.example.taimin.clases.elementos

import java.time.LocalTime
import androidx.room.Entity
import androidx.room.Ignore
import com.example.taimin.clases.Adjunto
import com.example.taimin.clases.Evento
import com.example.taimin.clases.EventoInterno
import com.example.taimin.clases.Usuario
import java.util.*

/**
 * Esta clase hereda de Elemento y posee la información relativa a las Tareas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
@Entity(tableName = "tabla_tareas")
class Tarea(usuario: Usuario?) : ElementoCreable(1, usuario){
    var duracion = 0.0
    private var horaIni: LocalTime? = null
    private var horaFin: LocalTime? = null
    @Ignore
    private var adjuntos = mutableListOf<Adjunto>()
    @Ignore
    private var eventos = mutableListOf<Evento>()
    @Ignore
    private var recordatorios = mutableListOf<EventoInterno>()
    constructor(id: UUID, bool: Boolean) : this(null) {
        this.setId(id)
    }

    fun getEventos():List<Evento>{
        return eventos
    }
    fun getRecordatorios(): List<EventoInterno>{
        return recordatorios
    }

    fun getHoraIni(): LocalTime?{
        return this.horaIni
    }
    fun getHoraFin(): LocalTime?{
        return this.horaFin
    }
    fun setHoraIni(d: LocalTime?){
        horaIni = d
    }
    fun setHoraFin(d: LocalTime?){
        horaFin = d
    }

    override fun duplicar(): Tarea {
        var copia = super.duplicar(Tarea(this.getUsuario())) as Tarea
        copia.duracion = this.duracion
        copia.horaIni = this.horaIni
        copia.horaFin = this.horaFin
        return copia
    }
}

