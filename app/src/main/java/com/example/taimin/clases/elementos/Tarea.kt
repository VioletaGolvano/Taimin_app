package elementos

import Adjunto
import Usuario
import java.util.*
/**
 * Esta clase hereda de Elemento y posee la información relativa a las Tareas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
class Tarea(user: Usuario) : ElementoCreable(1, user){
    private var duracion = 0.0
    private lateinit var horaIni: Date
    private lateinit var horaFin: Date
    private var adjuntos = mutableListOf<Adjunto>()

    override fun duplicar(): Tarea {
        var copia = super.duplicar(Tarea(this.getUser())) as Tarea
        copia.duracion = this.duracion
        copia.horaIni = this.horaIni
        copia.horaFin = this.horaFin
        return copia
    }

}