package com.example.taimin.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taimin.R

class AddElemento : Fragment() {
    companion object {
        fun newInstance(): AddElemento = AddElemento()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_add_elemento, container, false)
}