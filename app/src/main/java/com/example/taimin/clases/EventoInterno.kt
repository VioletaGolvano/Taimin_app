import java.util.*

class EventoInterno : Evento() {
    lateinit var hora: Date
    fun duplicar(): EventoInterno {
        var copia = EventoInterno()
        copia.hora = this.hora
        return copia
    }

}
