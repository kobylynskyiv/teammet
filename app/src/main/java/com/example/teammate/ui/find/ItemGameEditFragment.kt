package com.example.teammate.ui.find

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentEditGameBinding
import com.example.teammate.interfaces.ISelectedCity
import com.example.teammate.ui.city.adapter.CityItems
import com.example.teammate.ui.find.adapter.FoundItems
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("ParcelCreator")
class ItemGameEditFragment : Fragment(), ISelectedCity {

    private var _binding: FragmentEditGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var items : FoundItems
    private var data : CityItems? = null
    private var flag = false
    private var myCalendar : Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateLabel() {
        val myFormat = "MM/dd/yy HH:mm"  //In which you need put here
        val sdf = SimpleDateFormat(myFormat)
        binding.tyDate.setText(sdf.format(myCalendar.time))
    }

    @SuppressLint("ClickableViewAccessibility", "ShowToast", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val title : TextView = requireActivity().findViewById(R.id.toolbar_title)
        title.text = "Редактирование игры"

        items = requireArguments().getSerializable("array") as FoundItems

        val convert = SimpleDateFormat("dd MMMM \n HH:mm")

        binding.tyName.setText(items.title)
        binding.tyCity.setText(items.city?.get("city").toString())
        binding.tyComments.setText(items.comment)
        val date = when (items.date?.toDate()?.day) {
            Date().day -> {
                "Сегодня \n ${items.date?.toDate()?.hours}:${items.date?.toDate()?.minutes}" +
                        "${ when(items.date?.toDate()?.minutes) {
                            0,1,2,3,4,5,6,7,8,9 -> "0"
                            else -> ""
                        }} "
            }
            Date().day + 1 -> {
                "Завтра \n ${items.date?.toDate()?.hours}:${items.date?.toDate()?.minutes}" +
                        "${ when(items.date?.toDate()?.minutes) {
                            0,1,2,3,4,5,6,7,8,9 -> "0"
                            else -> ""
                        }} "
            }
            else -> {
                convert.format(items.date?.toDate())
            }
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


        binding.tyDate.setText(date.replace("\n", ""))
        binding.tyPlace.setText(items.location)
        binding.tySport.setText(items.sport)

        val navigation = Navigation.findNavController(view)


        binding.tySport.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.item_sport, null)
            dialogBuilder.setView(dialogView)
            val cats = arrayOf("Футбол", "Волейбол", "Баскетбол", "Хоккей")
            val numberPicker = dialogView.findViewById<View>(R.id.numberPicker) as NumberPicker
            numberPicker.minValue = 0
            numberPicker.maxValue = 3
            numberPicker.displayedValues = cats

            dialogBuilder.setPositiveButton("Выбрать") { dialog, _ ->
                binding.tySport.setText(cats[numberPicker.value])
                dialog.dismiss()
            }
            dialogBuilder.show()
        }

        binding.tyCity.setOnTouchListener { _, _ ->
            if (navigation.currentDestination?.id == R.id.itemGameEditFragment) {
                val bundle = Bundle()
                bundle.putParcelable("fragment", this)
                view.findNavController().navigate(R.id.action_itemGameEditFragment_to_cityFragment, bundle)
            }
            true
        }

        binding.relativeLayout.setOnClickListener {
            val imm: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = requireActivity().currentFocus
            if (view == null) {
                view = View(activity)
            }

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


        binding.btnSave.setOnClickListener {

            if(binding.tyName.text?.toString().equals("")||
                binding.tySport.text?.toString().equals("")  ||
                binding.tyCity.text?.toString().equals("") ||
                binding.tyPlace.text?.toString().equals("") ||
                binding.tyDate.text?.toString().equals("")){
                binding.error.visibility = View.VISIBLE
            }else {

                binding.error.visibility = View.GONE

                items.title = binding.tyName.text.toString()
                items.comment = binding.tyComments.text.toString()
                items.location = binding.tyPlace.text.toString()
                items.sport = binding.tySport.text.toString()

                if (data?.city != null || data?.region != null) {
                    val cityItems = mutableMapOf("city" to data?.city, "region" to data?.region)
                    items.city = cityItems as HashMap<*, *>
                }

                items.date = Timestamp(myCalendar.time)

                val db = FirebaseFirestore.getInstance()
                db.collection("games").document(items.gameId).set(items).addOnCompleteListener {
                    Snackbar.make(view, "Данные изменены", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnRemove.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder
                .setTitle("Вы уверены, что хотите удалить игру?")
                .setPositiveButton("Удалить") { _, _ ->
                    val db = FirebaseFirestore.getInstance()
                    db.collection("games").document(items.gameId).delete()
                    view.findNavController()
                        .navigate(R.id.action_itemGameEditFragment_to_profileFragment)
                    Snackbar.make(view, "Данные удалены", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("Отмена") { dialog, id ->
                    dialog.dismiss()
                }

            builder.show()

        }

    }

    override fun onResume() {
        super.onResume()
        flag = true
        if(data != null) {
            binding.tyCity.setText(data?.city)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun selectedCity(items: CityItems, view: View?) {
        this.data = items
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {}

}