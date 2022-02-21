package com.example.taimin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PantallasArchivo : Fragment() {
    companion object {
        fun newInstance(): PantallasArchivo = PantallasArchivo()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_pantallas_archivo, container, false)
}