package com.example.taimin.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.taimin.clases.Evento
import com.example.taimin.clases.Usuario
import com.example.taimin.clases.elementos.*

@Dao
interface TaiminDAO {
    @Query("SELECT * FROM tabla_usuario LIMIT 1")
    fun getUsuario(): Usuario?


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

    @Query("SELECT * FROM tabla_evento")
    fun getEventosList(): List<Evento>?

    @Insert
    fun addUsuario(usuario: Usuario)

    fun addElemento(elemento: Elemento){
        when(elemento){
            is Proyecto -> addProyecto(elemento)
            is Lista -> addLista(elemento)
            is Tarea -> addTarea(elemento)
            is Subtarea -> addSubtarea(elemento)
        }
    }

    @Insert
    fun addPantalla(pantalla: Pantalla)
    @Insert(onConflict = REPLACE)
    fun addProyecto(proyecto: Proyecto)
    @Insert(onConflict = REPLACE)
    fun addLista(lista: Lista)
    @Insert(onConflict = REPLACE)
    fun addTarea(tarea: Tarea)
    @Insert(onConflict = REPLACE)
    fun addSubtarea(subtarea: Subtarea)

    @Insert(onConflict = REPLACE)
    fun addEvento(evento: Evento)

    @Delete
    fun delProyecto(proyecto: Proyecto)
    @Delete
    fun delLista(lista: Lista)
    @Delete
    fun delTarea(tarea: Tarea)
    @Delete
    fun delSubtarea(subtarea: Subtarea)
    @Delete
    fun delEvento(evento: Evento)

    fun delElemento(elemento: Elemento){
        when(elemento){
            is Proyecto -> delProyecto(elemento)
            is Lista -> delLista(elemento)
            is Tarea -> delTarea(elemento)
            is Subtarea -> delSubtarea(elemento)
        }
    }



}