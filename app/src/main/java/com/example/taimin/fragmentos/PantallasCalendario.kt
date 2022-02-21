package com.example.taimin.fragmentos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.taimin.R

class PantallasCalendario : Fragment() {
    companion object {
        fun newInstance(): PantallasCalendario = PantallasCalendario()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_pantallas_calendario, container, false)
}