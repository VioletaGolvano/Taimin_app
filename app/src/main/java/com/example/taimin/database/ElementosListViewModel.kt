package com.example.taimin.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.taimin.TaiminApplication
import com.example.taimin.clases.elementos.*

class ElementosListViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private var elementoSeleccionado =  MutableLiveData<String>()

    val elementosTotales: List<Elemento> = TaiminDatabase.getInstance(context).taiminDAO.getElementos()
    val pantallas: LiveData<List<Pantalla>> = TaiminDatabase.getInstance(context).taiminDAO.getPantallas()
    val proyectos: LiveData<List<Proyecto>> = TaiminDatabase.getInstance(context).taiminDAO.getProyectos()
    val listas: LiveData<List<Lista>> = TaiminDatabase.getInstance(context).taiminDAO.getListas()
    val tareas: LiveData<List<Tarea>> = TaiminDatabase.getInstance(context).taiminDAO.getTareas()
    val subtareas: LiveData<List<Subtarea>> = TaiminDatabase.getInstance(context).taiminDAO.getSubtareas()

    val elementos: LiveData<List<Elemento>> = Transformations.switchMap(elementoSeleccionado) {
        TaiminDatabase.getInstance(context as TaiminApplication).taiminDAO.getElementos(it)
    }

    fun cargarElemento(elemento: Elemento){
        elementoSeleccionado.value = elemento.getId().toString()
    }



}