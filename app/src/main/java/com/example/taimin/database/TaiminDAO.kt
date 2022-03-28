package com.example.taimin.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taimin.clases.Usuario
import com.example.taimin.clases.elementos.*

@Dao
interface TaiminDAO {
    fun getElementos(): List<Elemento> {
        var lista = mutableListOf<Elemento>()
        getPantallas().value?.let { lista.addAll(it) }
        getProyectos().value?.let { lista.addAll(it) }
        getListas().value?.let { lista.addAll(it) }
        getTareas().value?.let { lista.addAll(it) }
        getSubtareas().value?.let { lista.addAll(it) }
        return lista
    }

    @Query("SELECT * FROM tabla_usuario LIMIT 1")
    fun getUsuario(): Usuario?


    @Query("SELECT * FROM tabla_pantallas")
    fun getPantallas(): LiveData<List<Pantalla>>
    @Query("SELECT * FROM tabla_pantallas WHERE usuario = :usuario")
    fun getPantallas(usuario: String): List<Pantalla>

    @Query("SELECT * FROM tabla_proyectos")
    fun getProyectos(): LiveData<List<Proyecto>>

    @Query("SELECT * FROM tabla_listas")
    fun getListas(): LiveData<List<Lista>>

    @Query("SELECT * FROM tabla_tareas")
    fun getTareas(): LiveData<List<Tarea>>

    @Query("SELECT * FROM tabla_subtareas")
    fun getSubtareas(): LiveData<List<Subtarea>>

    fun getElementos(id: String): LiveData<List<Elemento>?>{
        var lista = MutableLiveData<List<Elemento>?>()
        lista.value = lista.value?.plus(getProyectoConContenedor(id) as List<Elemento>)
        lista.value = lista.value?.plus(getListaConContenedor(id) as List<Elemento>)
        lista.value = lista.value?.plus(getTareaConContenedor(id) as List<Elemento>)
        lista.value = lista.value?.plus(getSubtareaConContenedor(id) as List<Elemento>)
        return lista
    }


    @Query("SELECT * FROM tabla_pantallas")
    fun getPantallasList(): List<Pantalla>?
    @Query("SELECT * FROM tabla_proyectos")
    fun getProyectosList(): List<Proyecto>?
    @Query("SELECT * FROM tabla_listas")
    fun getListasList(): List<Lista>?
    @Query("SELECT * FROM tabla_tareas")
    fun getTareasList(): List<Tarea>?
    @Query("SELECT * FROM tabla_subtareas")
    fun getSubtareasList(): List<Subtarea>?


    @Query("SELECT * FROM tabla_proyectos WHERE contenedor = :id")
    fun getProyectoConContenedor(id: String): LiveData<Proyecto?>

    @Query("SELECT * FROM tabla_listas WHERE contenedor = :id")
    fun getListaConContenedor(id: String): LiveData<Lista?>

    @Query("SELECT * FROM tabla_tareas WHERE contenedor = :id")
    fun getTareaConContenedor(id: String): LiveData<Tarea?>

    @Query("SELECT * FROM tabla_subtareas WHERE contenedor = :id")
    fun getSubtareaConContenedor(id: String): LiveData<Subtarea?>


    @Query("SELECT * FROM tabla_pantallas WHERE id = :id")
    fun getPantalla(id: String): LiveData<Pantalla?>
    @Query("SELECT * FROM tabla_pantallas WHERE usuario = :usuario and titulo = :titulo")
    fun getPantalla(usuario: String, titulo: String): LiveData<Pantalla?>

    @Query("SELECT * FROM tabla_proyectos WHERE id = :id")
    fun getProyecto(id: String): LiveData<Proyecto?>

    @Query("SELECT * FROM tabla_listas WHERE id = :id")
    fun getLista(id: String): LiveData<Lista?>

    @Query("SELECT * FROM tabla_tareas WHERE id = :id")
    fun getTarea(id: String): LiveData<Tarea?>

    @Query("SELECT * FROM tabla_subtareas WHERE id = :id")
    fun getSubtarea(id: String): LiveData<Subtarea?>

    @Insert
    fun addUsuario(usuario: Usuario)

    @Insert
    fun addPantalla(pantalla: Pantalla)

    @Insert
    fun addProyecto(proyecto: Proyecto)

    @Insert
    fun addLista(lista: Lista)

    @Insert
    fun addTarea(tarea: Tarea)

    @Insert
    fun addSubtarea(subtarea: Subtarea)



}