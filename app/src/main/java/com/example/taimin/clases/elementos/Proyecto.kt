package com.example.taimin.clases.elementos

import androidx.room.Entity
import com.example.taimin.clases.Usuario
import java.util.*

/**
 * Esta clase hereda de Elemento y posee la información relativa a los Proyectos del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
@Entity(tableName = "tabla_proyectos")
class Proyecto(usuario: Usuario?): ElementoCreable(3, usuario) {
    override fun duplicar(): Proyecto {
        return super.duplicar(Proyecto(this.getUsuario())) as Proyecto
    }
    constructor(id: UUID, bool: Boolean) : this(null) {
        this.setId(id)
    }
}