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
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.taimin.fragmentos.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import elementos.Proyecto
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    lateinit var gestureDetector: GestureDetector
    var x2:Float = 0.0f
    var x1:Float = 0.0f
    var addFragment = AddElemento()
    lateinit var previousFragment: Fragment
    lateinit var previousMenu: BottomNavigationView
    val usuario = Usuario("mail","pass")

    // Cambio de menús y pantallas
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
                bottom_pp.visibility=VISIBLE
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
                bottom_cal.visibility=VISIBLE
                true
            }
            R.id.add -> {
                openFragment(addFragment)
                bottom_add.visibility=VISIBLE
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
                bottom_ar.visibility=VISIBLE
                true
            }
            R.id.cancel -> {
                openFragment(previousFragment)
                previousMenu.visibility=VISIBLE
                true
            }
            R.id.accept -> {
                addFragment.aceptar()
                openFragment(previousFragment)
                previousMenu.visibility=VISIBLE
                true
            }
            else -> false
        }

    }

    init {
        for (i in 0 until 20){
            var pro = Proyecto(usuario)
            pro.setTitulo("Proyecto $i")
            pro.setColorElemento(R.color.color11)
            pro.aceptar()
        }
    }
    companion object{
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this,this)

        //openFragment(ppFragment)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_cal?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
        bottom_pp?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
        bottom_ar?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
        bottom_ar?.let {
            NavigationUI.setupWithNavController(it, navController)
        }

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        //val navController = navHostFragment.navController
        //NavigationUI.setupWithNavController(findViewById(R.id.),navController)

        bottom_add.setOnItemSelectedListener(nav)
        bottom_pp.setOnItemSelectedListener(nav)
        bottom_cal.setOnItemSelectedListener(nav)
        bottom_ar.setOnItemSelectedListener(nav)

        bottom_pp.visibility= VISIBLE

    }


    private fun openFragment(fragment: Fragment) {
        asignacionPrevious()
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.nav_host_fragment, fragment)
        trans.commit()

        bottom_pp.visibility=GONE
        bottom_cal.visibility=GONE
        bottom_ar.visibility=GONE
        bottom_add.visibility=GONE
    }

    private fun asignacionPrevious(){
        if (supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!=null){
            previousFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        }

        when {
            bottom_ar.visibility== VISIBLE -> {
                previousMenu = bottom_ar
            }
            bottom_cal.visibility== VISIBLE -> {
                previousMenu =bottom_cal
            }
            bottom_pp.visibility== VISIBLE -> {
                previousMenu =bottom_pp
            }
        }
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
        //return gestureScanner.onTouchEvent(ev);
        when (ev?.action){
            0 -> { x1 = ev.x }
            1 -> { x2 = ev.x
                val valueX:Float = x2-x1
                if(Math.abs(valueX) > MIN_DISTANCE){
                    //detectar swipe hacia la derecha =>
                    if(x2 > x1) {
                        //Si está en pp(default) -> daily
                        //Si está en pp(To Do) -> default
                        if (bottom_pp.visibility==VISIBLE){
                            swipePantallasPrincipales(false)
                        }
                    }else{
                        //Si está en pp(daily) -> default
                        //Si está en pp(default) -> To Do
                        if (bottom_pp.visibility==VISIBLE){
                            swipePantallasPrincipales(true)
                        }
                    }
                }
            }
        }
        return gestureDetector.onTouchEvent(ev)
    }
    fun swipePantallasPrincipales(izda: Boolean){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        var fr = navHostFragment!!.childFragmentManager.fragments[0] as PantallasPrincipales
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
}