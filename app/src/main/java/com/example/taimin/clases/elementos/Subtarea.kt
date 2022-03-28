package com.example.taimin.clases.elementos

import androidx.room.Entity
import com.example.taimin.clases.Usuario
import java.util.*

/**
 * Esta clase hereda de Elemento y posee la información relativa a las Subtareas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
@Entity(tableName = "tabla_subtareas")
class Subtarea(usuario: Usuario?): Elemento(0, usuario) {
    constructor(id: UUID, bool: Boolean) : this(null) {
        this.setId(id)
    }
}