package com.example.taimin.clases.elementos

import androidx.room.*
import java.util.*
import com.example.taimin.clases.Usuario
import org.jetbrains.annotations.Nullable

/**
 * Esta clase tiene la información básica que poseen cada uno de los distintos tipos de Elementos de la aplicación.
 *
 * Hijas:
 * @see ElementoCreable
 * @see Subtarea
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */

@Entity(tableName = "tabla_elemento")
abstract class Elemento (
    private var IDClase: Int,
    private var usuario: Usuario?
    ) {
    @PrimaryKey
    private var id: UUID = UUID.randomUUID()
    private var progreso = 0.0 //Hasta 1.0
    private var titulo = ""
    private var contenedor: Elemento? = null  //Elemento que lo contiene
    @Ignore
    var contenidos = mutableListOf<Elemento>() //Elementos que contiene

    constructor(id: UUID) : this(-1, null) {
        this.id = id
    }

    /* GETTERS */
    fun getId(): UUID { return this.id }
    fun getIDClase(): Int { return this.IDClase }

    fun getTitulo(): String { return this.titulo }
    fun getProgreso(): Double { return this.progreso }
    fun getContenedor(): Elemento? {
        return if (this.contenedor != null) this.contenedor else null
    }

    fun getUsuario(): Usuario { return this.usuario!! }

    /* SETTERS */
    fun setId(id: UUID) { this.id = id}
    fun setIDClase(IDClase: Int) { this.IDClase = IDClase }
    fun setTitulo(titulo: String){ this.titulo = titulo }
    fun setProgreso(progreso: Double){
        this.progreso = progreso
        if (this.contenedor!=null)
            this.contenedor!!.recalcularProgreso()
    }
    fun setContenedor(cont: Elemento?){
        if (cont == null) return
        if (this.IDClase <= cont.IDClase)
            this.contenedor = cont
    }
    fun setUsuario(usuario: Usuario){ this.usuario = usuario }

    /**
     * Calcula el progreso del Elemento basado en el progreso de los elementos que contiene
     */
    fun recalcularProgreso(){
        var aux = 0.0
        contenidos.forEach { aux += it.getProgreso() }
        setProgreso(aux/contenidos.size)
        if (this.contenedor!=null){ contenedor!!.recalcularProgreso() }
    }
    fun completado() {
        setProgreso(1.0)
        val cont = this.getContenedor()
        if (cont is ElementoCreable)
            cont.recalcularProgreso()
    }

    /**
     * Añade un Elemento a los contenidos y establece este Elemento como su contenedor.
     *
     * @param elemento a añadir
     */
    fun addContenido(elemento: Elemento){
        if (this.IDClase <= elemento.IDClase)
            return
        this.contenidos.add(elemento)
        if (elemento.contenedor!=null){
            elemento.getContenedor()!!.delContenido(elemento,true)
        }
        elemento.setContenedor(this)
    }

    private fun delContenido(elemento: Elemento, boolean: Boolean){
        this.contenidos.remove(elemento)
    }
    fun delContenido(elemento: Elemento){
        this.delContenido(elemento,true)
        getUsuario().getDefault().addContenido(elemento)
    }




    /* MÉTODOS */
    /**
     * Evalúa si el elemento está completado
     * @return true en caso afirmativo
     */
    fun isCompleted(): Boolean {
        if (this.progreso >= 1.0)
            return true
        return false
    }

    /**
     * Devuelve los elementos que pueden contener al elemento actual
     * @return Lista de elementos posibles
     */
    fun getElementoSuperior(): List<Elemento>{
        return usuario!!.getElementoSuperior(this)
    }

    /**
     * Añade el elemento a base de datos. Añade el elemento a los participantes.
     * TODO
     * @return si se ha añadido correctamente
     */
    open fun aceptar(): Boolean {
        if (this.getTitulo().isEmpty()){
            this.eliminar()
        }
        if (this.contenedor==null){
            getUsuario().getDefault().addContenido(this)
        }
        val elem = usuario!!.buscar(this.id)
        if (elem == null){
            getUsuario().addElemento(this)
        }
        // Actualizar todas las variables en la db
        return true
    }

    /**
     * Descarta todos los cambios hechos en el elemento
     * TODO
     * @return si se han podido descartar los cambios
     */
    fun cancelar(): Boolean {
        // Volver a cargar el elemento de la db
        return true
    }

    /**
     * Elimina el elemento de la base de datos
     * TODO
     */
    fun eliminar() {
        if (this.contenedor!=null)
            contenedor!!.delContenido(this, true)
        usuario!!.removeElemento(this)
    }

    /**
     * Muestra las características del elemento
     */
    open fun show() {
        println(this.titulo+" - ID clase: "+this.IDClase)
        println("\tCompleción: "+this.getProgreso()*100+"%")
        println("\tContenedor: "+this.contenedor?.getTitulo())
        if (this.contenidos.isNotEmpty()){
            println("\tContenidos:")
            this.contenidos.forEach { it -> println("\t\t"+it.titulo) }
        }

    }
}