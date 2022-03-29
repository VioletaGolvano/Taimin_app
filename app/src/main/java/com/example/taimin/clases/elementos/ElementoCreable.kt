package com.example.taimin.clases.elementos

import java.time.LocalDate
import com.example.taimin.R
import androidx.room.Entity
import androidx.room.Ignore
import com.example.taimin.clases.*
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * Esta clase hereda de Elemento y posee todos los atributos comunes a Proyectos, Listas y Tareas.
 *
 * @see Elemento
 *
 * Hijas:
 * @see Proyecto
 * @see Lista
 * @see Tarea
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
@Entity(tableName = "tabla_elementoCreable")
abstract class ElementoCreable(IDClase: Int, usuario: Usuario?) : Elemento(IDClase, usuario) {
    private var color: Int? = null
    private var fechaIni: LocalDate? = null
    private var fechaFin: LocalDate? = null
    private var descripcion: String? = null
    private var ordenColores = false

    private var repeticion: Repeticion? = null
    private var prioridad = Prioridad.NULA
    @Ignore
    private var recordatorio = mutableListOf<EventoInterno>()
    @Ignore
    private var participantes = mutableListOf<Usuario>()

    init {


    }

    /* GETTERS */
    fun getColor(): Int? { return this.color }
    fun getFechaIni(): LocalDate? { return this.fechaIni }
    fun getFechaFin(): LocalDate? { return this.fechaFin }
    fun getDescripcion(): String? { return this.descripcion }
    fun getOrdenColores(): Boolean { return this.ordenColores }
    fun getRepeticion(): Repeticion? { return this.repeticion }
    fun getPrioridad(): Prioridad { return this.prioridad }
    fun getRecordatorio(): List<EventoInterno>{ return this.recordatorio }
    fun getParticipantes(): List<Usuario>{ return this.participantes }

    /* SETTERS */
    fun setColor(color: Int){ this.color = color }
    fun setFechaIni(fecha: LocalDate?) { this.fechaIni = fecha }
    fun setFechaFin(fecha: LocalDate?) { this.fechaFin = fecha}
    fun setDescripcion(desc: String?) { this.descripcion = desc }
    fun setOrdenColores(col: Boolean) { this.ordenColores = col}
    fun setRepeticion(rep: Repeticion?) { this.repeticion = rep }
    fun setRecordatorio(recs: List<EventoInterno>?) { if(recs==null)return; this.recordatorio = recs!!.toMutableList() }
    fun setPrioridad(prioridad: Prioridad) { this.prioridad = prioridad }

    /* MÉTODOS */
    abstract fun duplicar(): ElementoCreable

    /**
     * Copia toda la información del ElementoCreable actual al pasado por argumento.
     *
     * @param copia al que hay que copiar la información
     * @return el ElementoCreable pasado como argumento modificado
     */
    fun duplicar(copia: ElementoCreable): ElementoCreable {
        copia.setTitulo(this.getTitulo())
        copia.setProgreso(this.getProgreso())
        copia.color = this.color
        copia.fechaIni = this.fechaIni
        copia.fechaFin = this.fechaFin
        copia.descripcion = this.descripcion
        copia.ordenColores = this.ordenColores
        copia.repeticion = this.repeticion
        copia.prioridad = this.prioridad
        this.recordatorio.forEach { copia.addRecordatorio(it.duplicar()) }
        this.getContenedor()?.addContenido(copia)
        this.contenidos.forEach { //Se duplican los contenidos
            if (it is ElementoCreable)
                copia.addContenido(it.duplicar())
        }
        return copia
    }

    /**
     * Calcula la prioridad del Elemento basada en la prioridad de los elementos que contiene
     */
    fun recalcularPrioridad(){
        var aux = Prioridad.NULA
        contenidos.forEach{
            if (it is ElementoCreable)
                if (it.prioridad > aux)
                    aux = it.prioridad
        }
        this.prioridad = aux
    }

    /**
     * Añade un participante al ElementoCreable
     *
     * @param user al que se comparte el elemento
     */
    fun addParticipante(user: Usuario){
        this.participantes.add(user)
    }

    /**
     * Elimina un participante del ElementoCreable
     *
     * @param user al que se revoca acceso al elemento
     */
    fun delParticipante(user: Usuario){
        this.participantes.remove(user)
    }

    /**
     * Añade un recordatorio al ElementoCreable
     *
     * @param eventoInterno que contiene la hora y el día del recordatorio
     */
    fun addRecordatorio(eventoInterno: EventoInterno){
        if (this.recordatorio.size < Constantes.NUM_RECORDATORIOS)
            this.recordatorio.add(eventoInterno)
    }

    /**
     * Elimina un recordatorio del ElementoCreable
     *
     * @param eventoInterno a eliminar
     */
    fun delRecordatorio(eventoInterno: EventoInterno){
        if (this.recordatorio.contains(eventoInterno))
            this.recordatorio.remove(eventoInterno)
    }

    override fun aceptar(): Boolean {
        if (this.fechaFin!=null){
            addEvento()
        }
        return super.aceptar()
    }

    fun addEvento(){
        var addEnUser = false
        var eventos = this.getUsuario().getEventos().filter { it -> it.idElemento == this.getId() }
        if (eventos.isEmpty()){
            addEnUser = true
            eventos = listOf(Evento())
        }

        eventos.forEach { ev ->

            if (this.getFechaFin()!=null){
                if (this is Tarea && this.getHoraIni()!=null && this.getHoraFin()!=null){
                    ev.setEvento(this.getId(), this.getTitulo(), this.getFechaFin()!!, this.getHoraIni()!!, this.getHoraFin()!!)
                }
                else {
                    ev.setEvento(this.getId(), this.getTitulo(), this.getFechaFin()!!)
                }
            }
            ev.evento.color = this.color!!
            if (addEnUser)
                this.getUsuario().addEvento(ev)
        }
    }
}
