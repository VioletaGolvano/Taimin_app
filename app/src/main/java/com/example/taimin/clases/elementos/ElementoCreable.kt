package elementos

import Constantes
import EventoInterno
import Prioridad
import Repeticion
import Usuario
import android.graphics.Color
import petrov.kristiyan.colorpicker.ColorPicker
import java.util.*

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
abstract class ElementoCreable(IDClase: Int, user: Usuario) : Elemento(IDClase, user) {
    private var color: Int? = null
    private lateinit var fechaIni: Date
    private lateinit var fechaFin: Date
    private lateinit var descripcion: String
    private var ordenColores = false

    private lateinit var repeticion: Repeticion
    private var prioridad = Prioridad.NULA
    private var recordatorio = mutableListOf<EventoInterno>()
    private var participantes = mutableListOf<Usuario>()

    init {


    }

    /* GETTERS */
    fun getColorElemento(): Int? { return this.color }
    fun getFechaIni(): Date { return this.fechaIni }
    fun getFechaFin(): Date{ return this.fechaFin }
    fun getDescripcion(): String{ return this.descripcion }
    fun getOrdenColores(): Boolean{ return this.ordenColores }
    fun getRepeticion(): Repeticion { return this.repeticion }
    fun getPrioridad(): Prioridad { return this.prioridad }
    fun getRecordatorio(): List<EventoInterno>{ return this.recordatorio }
    fun getParticipantes(): List<Usuario>{ return this.participantes }

    /* SETTERS */
    fun setColorElemento(color: Int){ this.color = color }
    fun setFechaIni(fecha: Date) { this.fechaIni = fecha }
    fun setFechaFin(fecha: Date) { this.fechaFin = fecha}
    fun setDescripcion(desc: String) { this.descripcion = desc }
    fun setOrdenColores(col: Boolean) { this.ordenColores = col}
    fun setRepeticion(rep: Repeticion) { this.repeticion = rep }
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
        copia.setContenedor(this.getContenedor()!!)
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

}
