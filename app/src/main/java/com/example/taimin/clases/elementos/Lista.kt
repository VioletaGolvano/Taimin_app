package com.example.taimin.clases.elementos

import androidx.room.Entity
import com.example.taimin.clases.Usuario
import java.util.*


/**
 * Esta clase hereda de Elemento y posee la información relativa a las Listas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
@Entity(tableName = "tabla_listas")
class Lista(usuario: Usuario?): ElementoCreable(2, usuario) {
    override fun duplicar(): Lista {
        return super.duplicar(Lista(this.getUsuario())) as Lista
    }
    constructor(id: UUID, bool: Boolean) : this(null) {
        this.setId(id)
    }
}