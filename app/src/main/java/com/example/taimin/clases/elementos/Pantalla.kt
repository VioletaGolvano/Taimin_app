package elementos

import Usuario

/**
 * Esta clase hereda de Elemento y posee la información relativa a las Pantallas del Usuario
 *
 * @see Elemento
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
class Pantalla(nombre: String, user: Usuario): Elemento(4, user) {
    init {
        super.setTitulo(nombre)
    }
    override fun show(){
        println(this.getTitulo()+" - ID clase: "+this.getIDClase())
        if (this.contenidos.isNotEmpty()){
            println("\tContenidos:")
            this.contenidos.forEach { it -> println("\t\t"+it.getTitulo()) }
        }
    }
}