package com.example.taimin

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.example.taimin.fragmentos.AddElemento
import com.example.taimin.fragmentos.PantallasArchivo
import com.example.taimin.fragmentos.PantallasCalendario
import com.example.taimin.fragmentos.PantallasPrincipales
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ppFragment = PantallasPrincipales()
    private val calFragment = PantallasCalendario()
    private val addFragment = AddElemento()
    private val arFragment = PantallasArchivo()
    private lateinit var previousFragment: Fragment
    private lateinit var previousMenu: BottomNavigationView


    private val nav = NavigationBarView.OnItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.inbox -> {
                openFragment(ppFragment)
                bottom_pp.visibility=VISIBLE
                true
            }
            R.id.calendar -> {
                openFragment(calFragment)
                bottom_cal.visibility=VISIBLE
                true
            }
            R.id.add -> {
                openFragment(addFragment)
                bottom_add.visibility=VISIBLE
                true
            }
            R.id.archive -> {
                openFragment(arFragment)
                bottom_ar.visibility=VISIBLE
                true
            }
            R.id.cancel -> {
                openFragment(previousFragment)
                previousMenu.visibility=VISIBLE
                true
            }
            R.id.accept -> {
                openFragment(previousFragment)
                previousMenu.visibility=VISIBLE
                true
            }
            else -> false
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(ppFragment)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        bottom_add.setOnItemSelectedListener(nav)
        bottom_pp.setOnItemSelectedListener(nav)
        bottom_cal.setOnItemSelectedListener(nav)
        bottom_ar.setOnItemSelectedListener(nav)

        bottom_pp.visibility= VISIBLE

    }


    private fun openFragment(fragment: Fragment) {
        asignacionPrevious()
        if (fragment!=null){
            val trans = supportFragmentManager.beginTransaction()
            trans.replace(R.id.fragment_pp, fragment)
            trans.commit()

            bottom_pp.visibility=GONE
            bottom_cal.visibility=GONE
            bottom_ar.visibility=GONE
            bottom_add.visibility=GONE
        }
    }

    private fun asignacionPrevious(){
        if (supportFragmentManager.findFragmentById(R.id.fragment_pp)!=null){
            previousFragment = supportFragmentManager.findFragmentById(R.id.fragment_pp)!!
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
}