package com.example.taimin.clases.elementos


import androidx.room.Entity
import com.example.taimin.clases.Usuario
import java.util.*

/**
 * Esta clase hereda de Elemento y posee la información relativa a las Pantallas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */

@Entity(tableName = "tabla_pantallas")
class Pantalla(titulo: String, usuario: Usuario?): Elemento(4, usuario) {
    init {
        super.setTitulo(titulo)
    }
    constructor(id: UUID, bool: Boolean, titulo: String) : this(titulo,null) {
        this.setId(id)
    }
    override fun show(){
        println(this.getTitulo()+" - ID clase: "+this.getIDClase())
        if (this.contenidos.isNotEmpty()){
            println("\tContenidos:")
            this.contenidos.forEach { it -> println("\t\t"+it.getTitulo()) }
        }
    }
}