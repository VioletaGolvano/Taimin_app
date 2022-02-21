package elementos

import Usuario

/**
 * Esta clase hereda de Elemento y posee la información relativa a los Proyectos del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
class Proyecto(user: Usuario): ElementoCreable(3, user) {
    override fun duplicar(): Proyecto {
        return super.duplicar(Proyecto(this.getUser())) as Proyecto
    }
}