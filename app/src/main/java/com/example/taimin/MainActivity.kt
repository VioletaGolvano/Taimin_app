package com.example.taimin

import Usuario
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.taimin.databinding.FragmentVerElementoBinding
import com.example.taimin.fragmentos.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import elementos.Lista
import elementos.Proyecto
import elementos.Tarea
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.random.Random


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    lateinit var gestureDetector: GestureDetector
    var x2:Float = 0.0f
    var x1:Float = 0.0f
    val usuario = Usuario("mail","pass")

    // Cambio de menús y pantallas
    private val add = OnClickListener{ bottomAddElement() }
    private val nav = NavigationBarView.OnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.inbox -> {
                try {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(PantallasCalendarioDirections.actionPantallasCalendarioToPantallasPrincipales())
                }catch (e: Exception){
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(PantallasArchivoDirections.actionPantallasArchivoToPantallasPrincipales())
                }
                true
            }
            R.id.calendar -> {
                try {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToPantallasCalendario())
                }catch (e: Exception){
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(PantallasArchivoDirections.actionPantallasArchivoToPantallasCalendario())
                }
                true
            }
            R.id.add -> {
                crearElemento.visibility = VISIBLE
                noAniadir.visibility = VISIBLE
                crear_proyecto.setOnClickListener{
                    val proyecto = Proyecto(usuario)
                    proyecto.setTitulo(proyecto.getID().toString())
                    proyecto.aceptar()
                    try {
                        Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToAddElemento(null, 3))
                    }catch (e: Exception){
                        try {
                            Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(PantallasArchivoDirections.actionPantallasArchivoToAddElemento(null,3))
                        }catch (e: Exception){
                            Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(PantallasCalendarioDirections.actionPantallasCalendarioToAddElemento(null,3))
                        }
                    }
                }
                crear_lista.setOnClickListener{
                    try {
                        Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToAddElemento(null, 2))
                    }catch (e: Exception){
                        try {
                            Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(PantallasArchivoDirections.actionPantallasArchivoToAddElemento(null, 2))
                        }catch (e: Exception){
                            Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(PantallasCalendarioDirections.actionPantallasCalendarioToAddElemento(null, 2))
                        }
                    }
                }
                crear_tarea.setOnClickListener{
                    try {
                        Navigation.findNavController(this, R.id.nav_host_fragment)
                            .navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToAddElemento(null, 1))
                    }catch (e: Exception){
                        try {
                            Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(PantallasArchivoDirections.actionPantallasArchivoToAddElemento(null, 1))
                        }catch (e: Exception){
                            Navigation.findNavController(this, R.id.nav_host_fragment)
                                .navigate(PantallasCalendarioDirections.actionPantallasCalendarioToAddElemento(null,1))
                        }
                    }
                }

                true
            }
            R.id.archive -> {
                try {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(PantallasCalendarioDirections.actionPantallasCalendarioToPantallasArchivo())
                }catch (e: Exception){
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(PantallasPrincipalesDirections.actionPantallasPrincipalesToPantallasArchivo())
                }
                true
            }
            R.id.cancel -> {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                var ae = navHostFragment!!.childFragmentManager.fragments[0] as AddElemento
                ae.cancelar()
                Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                true
            }
            R.id.accept -> {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                var ae = navHostFragment!!.childFragmentManager.fragments[0] as AddElemento
                ae.aceptar()
                Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                true
            }
            else -> false
        }

    }


    companion object{
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this,this)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_cal?.let {NavigationUI.setupWithNavController(it, navController)}
        bottom_pp?.let {NavigationUI.setupWithNavController(it, navController)}
        bottom_ar?.let {NavigationUI.setupWithNavController(it, navController)}
        bottom_ar?.let {NavigationUI.setupWithNavController(it, navController)}

        noAniadir.setOnClickListener(add)

        bottom_add.setOnItemSelectedListener(nav)
        bottom_pp.setOnItemSelectedListener(nav)
        bottom_cal.setOnItemSelectedListener(nav)
        bottom_ar.setOnItemSelectedListener(nav)

        bottom_pp.visibility= VISIBLE

    }

    override fun onBackPressed() {
        if (Navigation.findNavController(this, R.id.nav_host_fragment).currentDestination?.id == R.id.addElemento) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            var ae = navHostFragment!!.childFragmentManager.fragments[0] as AddElemento
            ae.cancelar()
        }
        super.onBackPressed()
    }

    // MENÚS INFERIORES
    fun fragmentoActual(fragment: Fragment){
        when (fragment){
            is AddElemento -> {
                bottomAddElement()
                bottomBarAddElement()}
            is VerElemento -> noBottomBar()
            is PantallasPrincipales -> bottomBarPP()
            is PantallasArchivo -> bottomBarArchivo()
            is PantallasCalendario -> bottomBarCalendario()
        }
    }
    private fun bottomAddElement(){
        crearElemento?.visibility = GONE
        noAniadir?.visibility = GONE
    }
    fun noBottomBar() {
        bottom_pp?.visibility=GONE
        bottom_cal?.visibility=GONE
        bottom_ar?.visibility=GONE
        bottom_add?.visibility=GONE
    }
    fun bottomBarAddElement(){
        bottom_pp?.visibility=GONE
        bottom_cal?.visibility=GONE
        bottom_ar?.visibility=GONE
        bottom_add?.visibility=VISIBLE
    }
    fun bottomBarPP(){
        bottom_pp?.visibility= VISIBLE
        bottom_cal?.visibility=GONE
        bottom_ar?.visibility=GONE
        bottom_add?.visibility=GONE
    }
    fun bottomBarCalendario(){
        bottom_pp?.visibility=GONE
        bottom_cal?.visibility= VISIBLE
        bottom_ar?.visibility=GONE
        bottom_add?.visibility=GONE
    }
    fun bottomBarArchivo(){
        bottom_pp?.visibility= GONE
        bottom_cal?.visibility=GONE
        bottom_ar?.visibility= VISIBLE
        bottom_add?.visibility=GONE
    }


    // SWIPE MOVEMENTS - Gesture detector interface
    override fun onDown(e: MotionEvent?): Boolean = false
    override fun onShowPress(e: MotionEvent?){}
    override fun onSingleTapUp(e: MotionEvent?): Boolean = false
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float) = false
    override fun onLongPress(e: MotionEvent?) {}
    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float) = false
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        when (ev?.action){
            0 -> { x1 = ev.x }
            1 -> { x2 = ev.x
                val valueX:Float = x2-x1
                if(abs(valueX) > MIN_DISTANCE){
                    //detectar swipe hacia la derecha =>
                    if(x2 > x1) {
                        //Si está en pp(default) -> daily
                        //Si está en pp(To Do) -> default
                        if (bottom_pp.visibility==VISIBLE){
                            swipePantallasPrincipales(false)
                        } else if (bottom_ar.visibility== VISIBLE){
                            swipePantallasArchivo(false)
                        }
                    }else{
                        //Si está en pp(daily) -> default
                        //Si está en pp(default) -> To Do
                        if (bottom_pp.visibility==VISIBLE){
                            swipePantallasPrincipales(true)
                        } else if (bottom_ar.visibility== VISIBLE){
                            swipePantallasArchivo(true)
                        }
                    }
                }
            }
        }
        return gestureDetector.onTouchEvent(ev)
    }
    fun swipePantallasPrincipales(izda: Boolean){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fr = navHostFragment!!.childFragmentManager.fragments[0] as PantallasPrincipales
        if (izda){
            if (fr.binding.titulo.text.contains(usuario.getDefault().getTitulo())){
                fr.toDo()
            }else if(fr.binding.titulo.text.contains(usuario.getDaily().getTitulo())){
                fr.defaultpp()
            }
        }else{
            if (fr.binding.titulo.text.contains(usuario.getDefault().getTitulo())){
                fr.daily()
            }else if(fr.binding.titulo.text.contains(usuario.getToDo().getTitulo())){
                fr.defaultpp()
            }
        }
    }
    fun swipePantallasArchivo(izda: Boolean){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fr = navHostFragment!!.childFragmentManager.fragments[0] as PantallasArchivo
        if (izda){
            if (fr.binding.titulo.text.contains(usuario.getArchived().getTitulo())){
                fr.completed()
            }
        }else{
            if (fr.binding.titulo.text.contains(usuario.getCompleted().getTitulo())){
                fr.archived()
            }
        }
    }
}