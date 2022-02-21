package elementos

import Usuario

/**
 * Esta clase hereda de Elemento y posee la información relativa a las Subtareas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
class Subtarea(user: Usuario): Elemento(0, user) {
    @Override
    fun setContenedor(tarea: Tarea) {
        this.setContenedor(tarea)
    }
}