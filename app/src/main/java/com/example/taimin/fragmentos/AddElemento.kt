package com.example.taimin.fragmentos

import Repeticion
import Usuario
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.R.color.light_purple
import com.example.taimin.R.color.purple
import com.example.taimin.databinding.FragmentAddElementoBinding
import elementos.ElementoCreable
import elementos.Lista
import elementos.Proyecto
import elementos.Tarea
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener
import java.lang.StringBuilder
import kotlin.collections.ArrayList





class AddElemento : Fragment() {
    lateinit var binding: FragmentAddElementoBinding
    lateinit var elemento: ElementoCreable
    private var c = Calendar.getInstance()

    var valoresRepes = Repeticion.values().map { it -> it.name }.toTypedArray()
    var listaRepes = ArrayList<Int>()
    var repeSeleccionada = BooleanArray(valoresRepes.size)

    var valores = arrayOf("5 min", "10 min", "30 min", "1h", "2h", "6h", "24h", "48h")
    var lista = ArrayList<Int>()
    var selec = BooleanArray(valores.size)

    private var listenerFecha = View.OnClickListener{
        val textoFecha = requireView().findViewById(R.id.texto_fecha) as TextView

        if (textoFecha.text.isEmpty()){
            var dia = c.get(Calendar.DAY_OF_MONTH)
            var mes = c.get(Calendar.MONTH)
            var anyo = c.get(Calendar.YEAR)

            val datePicker = DatePickerDialog(
                requireActivity(),
                { view, nAnyo, nMes, nDia  ->
                    val date = LocalDate.of(nAnyo, nMes+1, nDia)
                    textoFecha.setText(date.format(DateTimeFormatter.ofPattern("EEE, dd LLL")))
                    // TODO: Actualizar fecha del elemento
                }, dia, mes, anyo
            )
            datePicker.updateDate(anyo, mes, dia)
            datePicker.show()
            val botonFecha = view?.findViewById(R.id.icono_calendario) as Button
            botonFecha.setBackgroundColor(resources.getColor(R.color.purple))
        }else{
            textoFecha.text = ""
            // TODO: Eliminar fecha del elemento
            val botonFecha = view?.findViewById(R.id.icono_calendario) as Button
            botonFecha.setBackgroundColor(resources.getColor(R.color.light_purple))
        }
    }
    private var listenerHora = View.OnClickListener{
        val textoHoraIni = requireView().findViewById(R.id.texto_hora_inicio) as TextView
        val textoHoraFin = requireView().findViewById(R.id.texto_hora_final) as TextView

        if (textoHoraIni.text.isEmpty() || textoHoraFin.text.isEmpty()){
            var hora = c.get(Calendar.HOUR_OF_DAY)
            var minuto = c.get(Calendar.MINUTE)

            val timeFin = TimePickerDialog(requireActivity(),{ view, hour, minute  ->
                val date = LocalTime.of(hour, minute)
                textoHoraFin.text = date.format(DateTimeFormatter.ofPattern("hh:mm"))
                // TODO: Actualizar hora del elemento
            }, hora, minuto, true
            )
            timeFin.updateTime(hora, minuto)
            timeFin.show()

            val timeIni = TimePickerDialog(requireActivity(),{ view, hour, minute  ->
                val date = LocalTime.of(hour, minute)
                textoHoraIni.text = date.format(DateTimeFormatter.ofPattern("hh:mm"))
                // TODO: Actualizar hora del elemento
            }, hora, minuto, true
            )
            timeIni.updateTime(hora, minuto)
            timeIni.show()
            val botonHora = view?.findViewById(R.id.icono_reloj) as Button
            botonHora.setBackgroundColor(resources.getColor(R.color.purple))
        }else{
            textoHoraIni.text = ""
            textoHoraFin.text = ""
            // TODO: Eliminar horas del elemento
            val botonHora = view?.findViewById(R.id.icono_reloj) as Button
            botonHora.setBackgroundColor(resources.getColor(R.color.light_purple))
        }


    }
    private var listenerColor = View.OnClickListener{
        val colorPicker = ColorPicker(activity)
        colorPicker.show()
        colorPicker.setOnChooseColorListener(object : OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                // TODO: Añadir color al elemento
                val botonHora = view?.findViewById(R.id.color) as Button
                botonHora.setBackgroundColor(color)
                elemento.setColorElemento(color)
            }

            override fun onCancel() {
                // TODO: Eliminar color del elemento
                val botonHora = view?.findViewById(R.id.color) as Button
                botonHora.setBackgroundColor(resources.getColor(R.color.blue))
            }
        })
    }

    private var listenerRepeticiones = View.OnClickListener {

        val selectorRepes = view?.findViewById(R.id.seleccion_repeticiones) as TextView
        val alerta = AlertDialog.Builder(activity)
        alerta.setTitle(R.string.seleeccionar_repeticiones)
        alerta.setCancelable(false)
        alerta.setMultiChoiceItems(valoresRepes, repeSeleccionada, object: DialogInterface.OnMultiChoiceClickListener {
            override fun onClick(dI:DialogInterface, opt: Int, isChecked: Boolean) {
                if (isChecked) {
                    listaRepes.add(opt)
                    Log.d("FUCK", "$listaRepes")
                    Collections.sort(listaRepes)
                } else {
                    for (j in 0..valoresRepes.size-1) {
                        if (listaRepes.get(j) == opt) {
                            listaRepes.remove(j)
                        }
                    }
                }
            }
        })

        alerta.setPositiveButton(R.string.accept, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                // TODO: añadir repeticion a Elemento
                var string = StringBuilder()

                for (j in 0..listaRepes.size-1){
                    string.append(valoresRepes[listaRepes[j]])
                    if(j != listaRepes.size-1){
                        string.append(", ")
                    }
                }
                selectorRepes.setText(string.toString())
            }

        })
        alerta.setNegativeButton(R.string.cancel, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                // TODO: eliminar repeticiones de Elemento
                dialog.dismiss()
            }

        })
        alerta.setNeutralButton(R.string.clear, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                // TODO: eliminar repeticiones de Elemento
                repeSeleccionada = BooleanArray(valoresRepes.size){false}
                Log.d("FUCK", "$repeSeleccionada")
                listaRepes.clear()
                selectorRepes.setText("")
            }
        })
        alerta.show()
    }
    private var listenerRecordatorios = View.OnClickListener {
        val selectorRepes = binding.seleccionRecordatorio
        val alerta = AlertDialog.Builder(activity)
        alerta.setTitle(R.string.seleeccionar_recordatorios)
        alerta.setCancelable(false)
        alerta.setMultiChoiceItems(valores, selec, object: DialogInterface.OnMultiChoiceClickListener {
            override fun onClick(dI:DialogInterface, opt: Int, isChecked: Boolean) {
                if (isChecked) {
                    lista.add(opt)
                    Collections.sort(lista)
                } else {
                    for (j in 0..valores.size-1) {
                        if (lista.get(j) == opt) {
                            lista.remove(j)
                        }
                    }
                }
            }
        })

        alerta.setPositiveButton(R.string.accept, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                // TODO: añadir repeticion a Elemento
                var string = StringBuilder()

                for (j in 0 until lista.size){
                    string.append(valores[lista[j]])
                    if(j != lista.size-1){
                        string.append(", ")
                    }
                }
                selectorRepes.setText(string.toString())
            }

        })
        alerta.setNegativeButton(R.string.cancel, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                // TODO: eliminar repeticiones de Elemento
                dialog.dismiss()
            }

        })
        alerta.setNeutralButton(R.string.clear, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                // TODO: eliminar repeticiones de Elemento
                selec = BooleanArray(valores.size){false}
                lista.clear()
                selectorRepes.setText("")
            }
        })
        alerta.show()
    }

    private var listenerPrioridadRojo = View.OnClickListener{
        var iconoPrioridad = requireView().findViewById(R.id.priority_warning) as ImageView
        iconoPrioridad.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_red_24, activity?.theme))
    }
    private var listenerPrioridadAmbar = View.OnClickListener{
        var iconoPrioridad = requireView().findViewById(R.id.priority_warning) as ImageView
        iconoPrioridad.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_amber_24, activity?.theme))
    }
    private var listenerPrioridadAzul= View.OnClickListener{
        var iconoPrioridad = requireView().findViewById(R.id.priority_warning) as ImageView
        iconoPrioridad.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_blue_24, activity?.theme))
    }
    private var listenerPrioridadGris = View.OnClickListener{
        var iconoPrioridad = requireView().findViewById(R.id.priority_warning) as ImageView
        iconoPrioridad.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_grey_24, activity?.theme))
    }

    private var listenerContenedor = View.OnClickListener {
        // TODO: desplegar selector con binding.elemento.
        val selectorContainer = (view?.findViewById(R.id.container) as TextView)
        val alerta = AlertDialog.Builder(activity)
        alerta.setTitle(R.string.contenedor)
        alerta.setCancelable(false)
        var elementos = binding.elemento!!.getElementoSuperior()
        var elementoSeleccionado = 0
        alerta.setSingleChoiceItems(elementos.map { it -> it.getTitulo() }.toTypedArray(), elementoSeleccionado, object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                elementoSeleccionado = which
            }
        })

        alerta.setPositiveButton(R.string.accept, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                selectorContainer.setText(elementos[elementoSeleccionado].getTitulo())
            }
        })
        alerta.setNegativeButton(R.string.cancel, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, opt: Int){
                dialog.dismiss()
            }
        })
        alerta.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // buscar qué hace este setContentViewer porque creo que es lo que me quita el menú inferior
        // buscar cómo tener un binding de un fragmento -> Fragment...Binding de una parte de una actividad
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_elemento, container, false)

        val args = AddElementoArgs.fromBundle(arguments!!)
        val usuario = (activity as MainActivity).usuario

        if (args.elementoId!=null){
            elemento = usuario.buscar(UUID.fromString(args.elementoId)) as ElementoCreable
        }else {
            when (args.elementoIDClase) {
                3 -> {
                    elemento = Proyecto(usuario)
                    elemento.setTitulo("PROYECTO NUEVO")
                }
                2 -> {
                    elemento = Lista(usuario)
                    elemento.setTitulo("LISTA NUEVA")
                }
                1 -> {
                    elemento = Tarea(usuario)
                    elemento.setTitulo("TAREA NUEVA")
                }
                else -> {
                    // Si el elemento se va a editar
                }
            }
        }

        binding.elemento = elemento

        var view = binding.root

        binding.iconoCalendario.setOnClickListener(listenerFecha)

        val botonHora = view.findViewById(R.id.icono_reloj) as Button
        botonHora.setOnClickListener(listenerHora)

        val botonColor = view.findViewById(R.id.color) as Button
        botonColor.setOnClickListener(listenerColor)

        val selectorRepes = view.findViewById(R.id.seleccion_repeticiones) as TextView
        selectorRepes.setOnClickListener(listenerRepeticiones)

        val selectorRecor = view.findViewById(R.id.seleccion_recordatorio) as TextView
        selectorRecor.setOnClickListener(listenerRecordatorios)

        binding.redPriority.setOnClickListener(listenerPrioridadRojo)
        binding.ambarPriority.setOnClickListener(listenerPrioridadAmbar)
        binding.bluePriority.setOnClickListener(listenerPrioridadAzul)
        binding.greyPriority.setOnClickListener(listenerPrioridadGris)

        binding.folder.setOnClickListener(listenerContenedor)


        return view
    }

    /**
     * Esta función actualiza todas las variables del elemento
     */
    fun aceptar() {
        //binding.elemento!!.show()
    }

    /* https://www.youtube.com/watch?v=4EKlAvjY74U - 4:40 (Attach file)
    var activityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            fun onActivityResult(result: ActivityResult){
                if (result.resultCode == activity.RESULT_OK){
                    var data = result.data
                    var uri = data?.data
                }
            }
        }
    )

    fun openFileDialog(View view){

    }
    */



}