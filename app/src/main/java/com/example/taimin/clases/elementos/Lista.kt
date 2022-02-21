package elementos

import Usuario

/**
 * Esta clase hereda de Elemento y posee la información relativa a las Listas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
class Lista(user: Usuario): ElementoCreable(2, user) {
    override fun duplicar(): Lista {
        return super.duplicar(Lista(this.getUser())) as Lista
    }
}