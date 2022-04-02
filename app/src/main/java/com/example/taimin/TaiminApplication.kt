package com.example.taimin

import android.app.Application
import com.example.taimin.clases.Usuario
import com.example.taimin.database.TaiminDatabase
import java.util.concurrent.Executors
import com.example.taimin.clases.elementos.*


class TaiminApplication: Application() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var usuario: Usuario

    init {
        usuario = Usuario("mail","pass")
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

            var eventos = taiminDatabase.taiminDAO.getEventosList()

            pantallas?.forEach { pantalla ->
                usuario.buscar(pantalla.getTitulo()).first().setId(pantalla.getId()) }
            proyectos?.forEach { proyecto ->
                proyecto.setUsuario(usuario)
                usuario.buscar(proyecto.getContenedor()!!.getId())!!.addContenido(proyecto)
                usuario.addElemento(proyecto)
            }
            listas?.forEach { lista ->
                lista.setUsuario(usuario)
                usuario.buscar(lista.getContenedor()!!.getId())!!.addContenido(lista)
                usuario.addElemento(lista)
            }
            tareas?.forEach { tarea ->
                tarea.setUsuario(usuario)
                usuario.buscar(tarea.getContenedor()!!.getId())!!.addContenido(tarea)
                usuario.addElemento(tarea)
            }
            subtareas?.forEach { subtarea ->
                subtarea.setUsuario(usuario)
                usuario.buscar(subtarea.getContenedor()!!.getId())!!.addContenido(subtarea)
                usuario.addElemento(subtarea)
            }
            eventos?.forEach { evento ->
                var elemento = usuario.buscar(evento.idElemento)
                if (elemento != null){
                    usuario.addEvento(evento)
                    evento.evento.color = (elemento as ElementoCreable).getColor()
                        ?: resources.getColor(R.color.color11)
                }else{
                    taiminDatabase.taiminDAO.delEvento(evento)
                }
            }
        }

    }
}