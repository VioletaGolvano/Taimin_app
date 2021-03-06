package com.example.taimin.fragmentos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.taimin.MainActivity
import com.example.taimin.R
import com.example.taimin.TaiminApplication
import com.example.taimin.databinding.FragmentAddElementoBinding
import com.example.taimin.clases.elementos.*
import com.example.taimin.clases.*
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
    lateinit var elementoCancelar: ElementoCreable
    private var c = Calendar.getInstance()

    var repeticionSeleccionada = 0
    var contenedorSeleccionado = 0

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
                    elemento.setFechaFin(date)
                }, dia, mes, anyo
            )
            datePicker.updateDate(anyo, mes, dia)
            datePicker.show()
            val botonFecha = view?.findViewById(R.id.icono_calendario) as Button
            botonFecha.setBackgroundColor(resources.getColor(R.color.purple))
        }else{
            textoFecha.text = ""
            elemento.setFechaFin(null)
            val botonFecha = view?.findViewById(R.id.icono_calendario) as Button
            botonFecha.setBackgroundColor(resources.getColor(R.color.light_purple))
        }
    }
    private var listenerHora = View.OnClickListener{
        val textoHoraIni = requireView().findViewById(R.id.texto_hora_inicio) as TextView
        val textoHoraFin = requireView().findViewById(R.id.texto_hora_final) as TextView

        if (textoHoraIni.text.isEmpty() && textoHoraFin.text.isEmpty()){
            var hora = c.get(Calendar.HOUR_OF_DAY)
            var minuto = c.get(Calendar.MINUTE)

            val timeFin = TimePickerDialog(requireActivity(),{ view, hour, minute  ->
                val date = LocalTime.of(hour, minute)
                textoHoraFin.text = date.format(DateTimeFormatter.ofPattern("hh:mm"))
                (elemento as Tarea).setHoraFin(date)
            }, hora, minuto, true
            )
            timeFin.updateTime(hora, minuto)
            (elemento as Tarea).setHoraFin(LocalTime.of(hora, minuto))
            timeFin.show()

            val timeIni = TimePickerDialog(requireActivity(),{ view, hour, minute  ->
                val date = LocalTime.of(hour, minute)
                textoHoraIni.text = date.format(DateTimeFormatter.ofPattern("hh:mm"))
                (elemento as Tarea).setHoraIni(date)
            }, hora, minuto, true
            )
            timeIni.updateTime(hora, minuto)
            (elemento as Tarea).setHoraIni(LocalTime.of(hora, minuto))
            timeIni.show()
            val botonHora = view?.findViewById(R.id.icono_reloj) as Button
            botonHora.setBackgroundColor(resources.getColor(R.color.purple))
        }else{
            textoHoraIni.text = ""
            textoHoraFin.text = ""
            (elemento as Tarea).setHoraFin(null)
            (elemento as Tarea).setHoraIni(null)
            val botonHora = view?.findViewById(R.id.icono_reloj) as Button
            botonHora.setBackgroundColor(resources.getColor(R.color.light_purple))
        }


    }
    private var listenerColor = View.OnClickListener{
        val colorPicker = ColorPicker(activity)
        colorPicker.show()
        colorPicker.setOnChooseColorListener(object : OnChooseColorListener {
            override fun onChooseColor(position: Int, color: Int) {
                binding.color.setBackgroundColor(color)
                elemento.setColor(color)
            }

            override fun onCancel() {
                val botonColor = view?.findViewById(R.id.color) as Button
                elemento.setColor(resources.getColor(R.color.color11))
                botonColor.setBackgroundColor(resources.getColor(R.color.color11))
            }
        })
    }

    private var listenerRepeticiones = View.OnClickListener {
        val alerta = AlertDialog.Builder(activity)
        alerta.setTitle(R.string.seleeccionar_repeticiones)
        alerta.setCancelable(false)
        var repeticiones = Repeticion.values().map { it -> getString(it.resource()) }.toTypedArray()
        alerta.setSingleChoiceItems(repeticiones, repeticionSeleccionada) { _, which ->
            repeticionSeleccionada = which }
        alerta.setPositiveButton(R.string.accept) { _, _ ->
            binding.seleccionRepeticiones.text = repeticiones[repeticionSeleccionada]
            elemento.setRepeticion(Repeticion.values().get(repeticionSeleccionada))
        }
        alerta.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        alerta.show()
    }
    private var listenerRecordatorios = View.OnClickListener {
        val selectorRecor = binding.seleccionRecordatorio
        val alerta = AlertDialog.Builder(activity)
        alerta.setTitle(R.string.seleeccionar_recordatorios)
        alerta.setCancelable(false)
        alerta.setMultiChoiceItems(valores, selec) { _, opt, isChecked ->
            if (isChecked) {
                lista.add(opt)
                lista.sort()
            } else {
                for (j in valores.indices) {
                    if (j < lista.size && lista[j] == opt) {
                        lista.remove(lista[j])
                    }
                }
            }
        }

        alerta.setPositiveButton(R.string.accept) { _, _ -> // TODO: a??adir recordatorio a Elemento
            var string = StringBuilder()

            for (j in 0 until lista.size) {
                string.append(valores[lista[j]])
                if (j != lista.size - 1) {
                    string.append(", ")
                }
            }
            selectorRecor.text = string.toString()
        }
        alerta.setNegativeButton(R.string.cancel) { dialog, _ -> // TODO: eliminar repeticiones de Elemento
            dialog.dismiss()
        }
        alerta.setNeutralButton(R.string.clear) { _, _ -> // TODO: eliminar repeticiones de Elemento
            selec = BooleanArray(valores.size) { false }
            lista.clear()
            selectorRecor.text = ""
        }
        alerta.show()
    }

    private var listenerPrioridadRojo = View.OnClickListener{
        binding.priorityWarning.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_red_24, activity?.theme))
        elemento.setPrioridad(Prioridad.ALTA)
    }
    private var listenerPrioridadAmbar = View.OnClickListener{
        binding.priorityWarning.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_amber_24, activity?.theme))
        elemento.setPrioridad(Prioridad.MEDIA)
    }
    private var listenerPrioridadAzul= View.OnClickListener{
        binding.priorityWarning.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_blue_24, activity?.theme))
        elemento.setPrioridad(Prioridad.BAJA)
    }
    private var listenerPrioridadGris = View.OnClickListener{
        binding.priorityWarning.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_warning_grey_24, activity?.theme))
        elemento.setPrioridad(Prioridad.NULA)
    }

    private var listenerContenedor = View.OnClickListener {
        val alerta = AlertDialog.Builder(activity)
        alerta.setTitle(R.string.contenedor)
        alerta.setCancelable(false)
        var elementos = binding.elemento!!.getElementoSuperior()
        contenedorSeleccionado = elementos.indexOf(elemento.getContenedor())
        alerta.setSingleChoiceItems(elementos.map { it -> it.getTitulo() }.toTypedArray(),
            contenedorSeleccionado) { _, which -> contenedorSeleccionado = which }
        alerta.setPositiveButton(R.string.accept) { _, _ ->
            binding.container.text = elementos[contenedorSeleccionado].getTitulo()
            elementos[contenedorSeleccionado].addContenido(elemento)
        }
        alerta.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        alerta.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_elemento, container, false)

        (activity as MainActivity).fragmentoActual(this)

        val args = AddElementoArgs.fromBundle(arguments!!)
        val usuario = (activity as MainActivity).usuario

        if (args.elementoId!=null){
            elemento = usuario!!.buscar(UUID.fromString(args.elementoId)) as ElementoCreable
        }else {
            when (args.elementoIDClase) {
                3 -> elemento = Proyecto(usuario!!)
                2 -> elemento = Lista(usuario!!)
                1 -> elemento = Tarea(usuario!!)
                else -> { }
            }
        }

        elementoCancelar = elemento.duplicar()
        binding.elemento = elemento

        binding.iconoCalendario.setOnClickListener(listenerFecha)
        binding.iconoReloj.setOnClickListener(listenerHora)
        binding.color.setOnClickListener(listenerColor)
        binding.repeticion.setOnClickListener(listenerRepeticiones)
        binding.recordatorio.setOnClickListener(listenerRecordatorios)

        binding.redPriority.setOnClickListener(listenerPrioridadRojo)
        binding.ambarPriority.setOnClickListener(listenerPrioridadAmbar)
        binding.bluePriority.setOnClickListener(listenerPrioridadAzul)
        binding.greyPriority.setOnClickListener(listenerPrioridadGris)

        binding.folder.setOnClickListener(listenerContenedor)
        binding.copia.setOnClickListener { copiar() }

        binding.color.setBackgroundColor(elemento.getColor()?:resources.getColor(R.color.color11))

        binding.textoFecha.text = elemento.getFechaFin()?.format(DateTimeFormatter.ofPattern("EEE, dd LLL"))
        if (elemento is Tarea){
            binding.textoHoraInicio.text = (elemento as Tarea).getHoraIni()?.format(DateTimeFormatter.ofPattern("hh:mm"))
            binding.textoHoraFinal.text = (elemento as Tarea).getHoraFin()?.format(DateTimeFormatter.ofPattern("hh:mm"))
        }

        binding.seleccionRepeticiones.text = elemento.getRepeticion()?.toString() ?: ""


        return binding.root
    }

    /**
     * Esta funci??n actualiza todas las variables del elemento
     */
    fun aceptar() {
        elemento.setTitulo(binding.titulo.text.toString())
        elemento.setDescripcion(binding.description.text.toString())
        if (elemento.getColor()==null)
            elemento.setColor(resources.getColor(R.color.color11))
        elemento.aceptar()
        elementoCancelar.eliminar()
    }

    fun cancelar() {
        elementoCancelar.duplicar(elemento)
        elementoCancelar.eliminar()
    }

    private fun copiar(){
        aceptar()
        var copia = elemento.duplicar()
        copia.setTitulo(copia.getTitulo()+"*")
        (activity as MainActivity).usuario!!.addElemento(copia)
        Navigation.findNavController((activity as MainActivity), R.id.nav_host_fragment).navigate(
            AddElementoDirections.actionAddElementoSelf(copia.getId().toString(), elemento.getIDClase()))
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