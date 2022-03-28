package com.example.taimin

import android.app.Application
import android.util.Log
import com.example.taimin.clases.Usuario
import com.example.taimin.database.TaiminDatabase
import java.lang.Exception
import java.util.concurrent.Executors
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.taimin.clases.elementos.*


class TaiminApplication: Application() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var usuario: Usuario
    lateinit var mainActivity: MainActivity
    lateinit var pantallasDB: List<Pantalla>
    lateinit var proyectosDB: MutableLiveData<List<Proyecto>>
    var listasDB: MutableLiveData<List<Lista>> = MutableLiveData()
    var tareasDB: MutableLiveData<List<Tarea>> = MutableLiveData()
    var subtareasDB: MutableLiveData<List<Subtarea>> = MutableLiveData()
    var count: MutableLiveData<Int> = MutableLiveData()

    init {
        usuario = Usuario("mail","pass")
        count.value = 0
    }
    private fun countMas(){
        count.value = count.value?.plus(1)
    }
    override fun onCreate() {
        super.onCreate()
        val taiminDatabase = TaiminDatabase.getInstance(context = this)
        executor.execute{
            var user = taiminDatabase.taiminDAO.getUsuario()
            if (user == null){
                taiminDatabase.taiminDAO.addUsuario(usuario)
                taiminDatabase.taiminDAO.addPantalla(usuario.getDefault())
                taiminDatabase.taiminDAO.addPantalla(usuario.getDaily())
                taiminDatabase.taiminDAO.addPantalla(usuario.getToDo())
                taiminDatabase.taiminDAO.addPantalla(usuario.getArchived())
                taiminDatabase.taiminDAO.addPantalla(usuario.getCompleted())
            }else{
                usuario = user
            }
        }
        executor.execute {
            var pantallas = taiminDatabase.taiminDAO.getPantallasList()
            var proyectos = taiminDatabase.taiminDAO.getProyectosList()
            var listas = taiminDatabase.taiminDAO.getListasList()
            var tareas = taiminDatabase.taiminDAO.getTareasList()
            var subtareas = taiminDatabase.taiminDAO.getSubtareasList()

            pantallas?.forEach { pantalla ->
                usuario.buscar(pantalla.getTitulo()).first().setId(pantalla.getId()) }
            proyectos?.forEach { proyecto ->
                usuario.buscar(proyecto.getContenedor()!!.getId())!!.addContenido(proyecto)
                usuario.addElemento(proyecto)
            }
            listas?.forEach { lista ->
                usuario.buscar(lista.getContenedor()!!.getId())!!.addContenido(lista)
                usuario.addElemento(lista)
            }
            tareas?.forEach { tarea ->
                usuario.buscar(tarea.getContenedor()!!.getId())!!.addContenido(tarea)
                usuario.addElemento(tarea)
            }
            subtareas?.forEach { subtarea ->
                usuario.buscar(subtarea.getContenedor()!!.getId())!!.addContenido(subtarea)
                usuario.addElemento(subtarea)
            }
        }
        /*
        executor.execute {
            proyectosDB = taiminDatabase.taiminDAO.getProyectos()
            countMas()
        }
         */
        /*
        executor.execute { listasDB = taiminDatabase.taiminDAO.getListas() }
        executor.execute { tareasDB = taiminDatabase.taiminDAO.getTareas() }
        executor.execute { subtareasDB = taiminDatabase.taiminDAO.getSubtareas() }
        */

        count.observeForever{ num ->
            if (num==5){
                pantallasDB.forEach { pantalla ->
                    usuario.buscar(pantalla.getTitulo()).first().setId(pantalla.getId()) }
                proyectosDB.value?.forEach { proyecto ->
                    usuario.buscar(proyecto.getContenedor()!!.getId())!!.addContenido(proyecto) }
                listasDB.value?.forEach { lista ->
                    usuario.buscar(lista.getContenedor()!!.getId())!!.addContenido(lista) }
                tareasDB.value?.forEach { tarea ->
                    usuario.buscar(tarea.getContenedor()!!.getId())!!.addContenido(tarea) }
                subtareasDB.value?.forEach { subtarea ->
                    usuario.buscar(subtarea.getContenedor()!!.getId())!!.addContenido(subtarea) }
            }
        }
        /*
        elementosDB.observeForever { elementos ->
            elementos?.forEach { elem ->
                if (elem.getContenedor() != null) {
                    usuario.buscar(elem.getContenedor()!!.getId())!!.addContenido(elem)
                }
                usuario.addElemento(elem)
            }
        }
         */

    }
}