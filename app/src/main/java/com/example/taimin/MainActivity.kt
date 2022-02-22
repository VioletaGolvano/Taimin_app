package com.example.taimin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.example.taimin.fragmentos.AddElemento
import com.example.taimin.fragmentos.PantallasArchivo
import com.example.taimin.fragmentos.PantallasCalendario
import com.example.taimin.fragmentos.PantallasPrincipales
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ppFragment = PantallasPrincipales()
    private val calFragment = PantallasCalendario()
    private val addFragment = AddElemento()
    private val arFragment = PantallasArchivo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(ppFragment)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        bottom_pp.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.calendar -> {
                    Log.d("FUCK", "Entra al calendario")
                    openFragment(calFragment)
                    bottom_cal.visibility=VISIBLE
                    true
                }
                R.id.add -> {
                    Log.d("FUCK", "Entra a añadir")
                    openFragment(addFragment)
                    true
                }
                R.id.archive -> {
                    Log.d("FUCK", "Entra al archivo")
                    openFragment(arFragment)
                    bottom_ar.visibility=VISIBLE
                    true
                }
                else -> false
            }

        }
        bottom_cal.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inbox -> {
                    Log.d("FUCK", "Entra al calendario")
                    openFragment(ppFragment)
                    bottom_pp.visibility=VISIBLE
                    true
                }
                R.id.add -> {
                    Log.d("FUCK", "Entra a añadir")
                    openFragment(addFragment)
                    true
                }
                R.id.archive -> {
                    Log.d("FUCK", "Entra al archivo")
                    openFragment(arFragment)
                    bottom_ar.visibility=VISIBLE
                    true
                }
                else -> false
            }

        }
        bottom_ar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.calendar -> {
                    Log.d("FUCK", "Entra al calendario")
                    openFragment(calFragment)
                    bottom_cal.visibility=VISIBLE
                    true
                }
                R.id.add -> {
                    Log.d("FUCK", "Entra a añadir")
                    openFragment(addFragment)
                    true
                }
                R.id.inbox -> {
                    Log.d("FUCK", "Entra al archivo")
                    openFragment(ppFragment)
                    bottom_pp.visibility=VISIBLE
                    true
                }
                else -> false
            }

        }
        bottom_pp.visibility= VISIBLE

    }

    private fun openFragment(fragment: Fragment) {
        if (fragment!=null){
            val trans = supportFragmentManager.beginTransaction()
            trans.replace(R.id.fragment_pp, fragment)
            trans.commit()

            bottom_pp.visibility=GONE
            bottom_cal.visibility=GONE
            bottom_ar.visibility=GONE
        }
    }
}