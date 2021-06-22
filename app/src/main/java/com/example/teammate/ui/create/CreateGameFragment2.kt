package com.example.teammate.ui.create

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentCreateGame2Binding
import com.example.teammate.interfaces.ISelectedCity
import com.example.teammate.ui.city.adapter.CityItems
import com.example.teammate.ui.find.adapter.FoundItems
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("ParcelCreator")
class CreateGameFragment2 : Fragment(), ISelectedCity {

    private var _binding: FragmentCreateGame2Binding? = null
    private val binding get() = _binding!!
    private lateinit var items : FoundItems
    private var data : CityItems? = null
    private var myCalendar : Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGame2Binding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateLabel() {
        val myFormat = "MM/dd/yy HH:mm"  //In which you need put here
        val sdf = SimpleDateFormat(myFormat)
        binding.tyDate.setText(sdf.format(myCalendar.time))
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SimpleDateFormat", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title : TextView? = activity?.findViewById(R.id.toolbar_title)
        title?.text = "Создание игры. Шаг 2"

        items = requireArguments().getSerializable("array") as FoundItems
        val navigation = Navigation.findNavController(view)

        binding.tyCity.setOnTouchListener { _, _ ->
            if (navigation.currentDestination?.id == R.id.createGameFragment2) {
                val bundle = Bundle()
                bundle.putParcelable("fragment", this)
                view.findNavController().navigate(R.id.action_createGameFragment2_to_cityFragment, bundle)
            }
            true
        }

        binding.tyDate.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.time_data, null)
            dialogBuilder.setView(dialogView)
            val dataPicker = dialogView.findViewById(R.id.datePicker) as DatePicker
            val timePicker = dialogView.findViewById(R.id.timePicker) as TimePicker
            timePicker.setIs24HourView(true)
            timePicker.currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            dialogBuilder.setPositiveButton("Продолжить") { _, _ ->
                myCalendar[Calendar.YEAR] = dataPicker.year
                myCalendar[Calendar.MONTH] = dataPicker.month
                myCalendar[Calendar.DAY_OF_MONTH] = dataPicker.dayOfMonth
                myCalendar[Calendar.HOUR_OF_DAY] = timePicker.currentHour
                myCalendar[Calendar.MINUTE] = timePicker.minute
                updateLabel()
            }

            dialogBuilder.show()
        }


        binding.goTo3.setOnClickListener {
            if(binding.tyCity.text?.isNotEmpty() == true &&
                binding.tyDate.text?.isNotEmpty() == true &&
                binding.tyPlace.text?.isNotEmpty() == true){
                binding.error.visibility = View.GONE
                val cityItems = mutableMapOf("city" to data?.city, "region" to data?.region)

                items.city = cityItems as HashMap<*, *>
                items.location = binding.tyPlace.text.toString()
                items.date = Timestamp(myCalendar.time)

                val bundle = Bundle()
                bundle.putSerializable("array", items)
                view.findNavController().navigate(R.id.action_createGameFragment2_to_createGameFragment3, bundle)
            }else{
                binding.error.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if(data != null) {
            binding.tyCity.setText(data?.city)
        }

    }
    override fun selectedCity(items: CityItems, view: View?) {
        this.data = items
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}