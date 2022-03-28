package com.example.taimin.clases
import com.example.taimin.clases.elementos.Elemento
import com.example.taimin.clases.elementos.Pantalla
import java.util.*
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Esta clase tiene la información relativa a los Usuarios de la aplicación.
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */
@Entity(tableName = "tabla_usuario")
class Usuario(
    private var mail: String,
    private var clave: String
) {
    @PrimaryKey
    var id: UUID = UUID.randomUUID()
    var email = mutableListOf<String>(mail)
    private var nombre = ""
    private var telefono = mutableListOf<String>()
    @Ignore
    var elementos = mutableListOf<Elemento>()
    @Ignore
    private var pantallasElementos = mutableListOf<Pantalla>()
    @Ignore
    private var eventos = mutableListOf<Evento>()

    init {
        val pantallas = listOf<Pantalla>(
            Pantalla("Daily", this), Pantalla("Default", this),
            Pantalla("ToDo",this), Pantalla("Archived",this), Pantalla("Completed",this)
        )
        pantallasElementos.addAll(pantallas)
        elementos.addAll(pantallas)
        addEmail(mail)
    }

    constructor(id: UUID): this("","") {
        this.id = id
    }
    fun setID(id: UUID) { this.id = id }
    fun setMail(mail: String) { this.mail = mail }
    fun setNombre(name: String) { this.nombre = name }
    fun setClave(clave: String) { this.clave = clave }
    fun setTelefono(tfn: List<String>) { this.telefono = tfn.toMutableList() }

    /* GETTERS */
    fun getID(): UUID { return this.id }
    fun getEmailList(): List<String> { return Collections.unmodifiableList(email) }
    fun getMail(): String { return this.mail }
    fun getNombre(): String { return nombre }
    fun getClave(): String { return clave }
    fun getTelefono(): List<String> { return Collections.unmodifiableList(telefono) }

    /* MÉTODOS */
    /**
     * Añade un email a la lista de correos
     *
     * @param mail que corresponde con otro email del usuario
     * @return true si el proceso se ha realizado con éxito
     */
    fun addEmail(mail: String): Boolean{
        if (email.size < Constantes.NUM_EMAIL && !email.contains(mail))
            return email.add(mail)
        return false
    }

    /**
     * Añade un teléfono a la lista de teléfonos
     *
     * @param tfn que corresponde con otro número de teléfono del usuario
     * @return true si el proceso se ha realizado con éxito
     */
    fun addPhone(tfn: String):Boolean{
        if (telefono.size < Constantes.NUM_TFN && !telefono.contains(tfn))
            return telefono.add(tfn)
        return false
    }

    /**
     * Añade un Elemento a la lista de elementos del Usuario
     *
     * @param elemento a añadir al Usuario
     * @return true si el proceso se ha realizado con éxito
     */
    fun addElemento(elemento: Elemento):Boolean{
        if (!elementos.contains(elemento))
            return elementos.add(elemento)
        return false
    }

    /**
     * Elimina un Elemento a la lista de elementos del Usuario
     *
     * @param elemento a eliminar al Usuario
     * @return true si el proceso se ha realizado con éxito
     */
    fun removeElemento(elemento: Elemento):Boolean{
        if (!elementos.contains(elemento))
            return true
        return elementos.remove(elemento)
    }

    /**
     * Devuelve los elementos completados de la lista de elementos del Usuario
     *
     * @return la lista de elementos
     */
    fun getElementosCompletados():List<Elemento>{
        return Collections.unmodifiableList(elementos.filter {
            it.getIDClase()<getDefault().getIDClase() && it.isCompleted()
        })
    }

    fun addEvento(evento: Evento){
        eventos.add(evento)
    }
    fun getEventos(): List<Evento>{
        return eventos
    }

    /**
     * Devuelve todos los Elementos que pueden contener el Elemento pasado por argumento
     *
     * @param elemento sobre el que se realiza la consulta
     * @return Lista de todos los Elementos que cumplen la característica
     */
    fun getElementoSuperior(elemento: Elemento) : List<Elemento> {
        val clase = elemento.getIDClase()
        return Collections.unmodifiableList(elementos.filter { it.getIDClase() > clase })
    }

    /**
     * Busca un Elemento entre todos los del Usuario
     *
     * @param s título del Elemento a buscar
     * @return Lista de los Elementos que coinciden con la búsqueda
     */
    fun buscar(s: String) : List<Elemento> {
        return Collections.unmodifiableList(elementos.filter { it.getTitulo().contains(s) })
    }
    fun buscar(id: UUID) : Elemento? {
        return elementos.find { it.getId()==id }
    }

    fun show(){
        println(this.getNombre()+" - "+this.id)
        println("\t"+this.getEmailList())
        if (getTelefono().isNotEmpty())
            println("\t"+this.getTelefono())

        if (elementos.isNotEmpty()){
            println("Elementos")
            elementos.forEach {
                it.show()
            }
        }


    }

    fun getDefault(): Pantalla {
        return this.pantallasElementos.filter { it.getTitulo().contains("Default") }[0]
    }
    fun getDaily(): Pantalla {
        return this.pantallasElementos.filter { it.getTitulo().contains("Daily") }[0]
    }
    fun getToDo(): Pantalla {
        return this.pantallasElementos.filter { it.getTitulo().contains("ToDo") }[0]
    }
    fun getArchived(): Pantalla {
        return this.pantallasElementos.filter { it.getTitulo().contains("Archived") }[0]
    }
    fun getCompleted(): Pantalla {
        return this.pantallasElementos.filter { it.getTitulo().contains("Completed") }[0]
    }

}
