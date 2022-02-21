package com.example.taimin.fragmentos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.taimin.R

class PantallasPrincipales : Fragment() {
    companion object {
        fun newInstance(): PantallasPrincipales = PantallasPrincipales()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_pantallas_principales, container, false)
}