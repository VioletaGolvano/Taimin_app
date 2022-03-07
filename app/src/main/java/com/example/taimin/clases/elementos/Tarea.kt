package elementos

import Adjunto
import Usuario
import java.time.LocalDate
import java.time.LocalTime
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
    private var horaIni: LocalTime? = null
    private var horaFin: LocalTime? = null
    private var adjuntos = mutableListOf<Adjunto>()

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
        var copia = super.duplicar(Tarea(this.getUser())) as Tarea
        copia.duracion = this.duracion
        copia.horaIni = this.horaIni
        copia.horaFin = this.horaFin
        return copia
    }

}