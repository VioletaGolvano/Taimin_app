package com.example.taimin.clases
/**
 * Esta clase tiene la información del Sistema
 *
 * @author  Violeta Golvano García
 * @version 1 05/02/2022
 */

class Sistema {
    private var participantes = mutableListOf<Usuario>()

    fun registrarse(email: String, clave: String){
        // Añadir usuario a la base de datos
    }

    fun logIn(email: String, clave: String){
        // Mirar si el Usuario y la clave coinciden, si no, dar mensaje de error
    }

    fun logOut(u: Usuario){
        // Salir de la app
    }

    fun eliminarUser(u: Usuario){
        // Eliminar usuario de la base de datos
    }

    fun buscarUser(nombre: String) {
        // Buscar usuario en la base de datos
    }

    fun notificacion(s: String){
        // Mostrar notificación pop-up
    }

}