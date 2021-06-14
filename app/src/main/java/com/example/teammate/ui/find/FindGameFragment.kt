package com.example.teammate.ui.find

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.teammate.R
import com.example.teammate.databinding.FragmentFindBinding
import com.example.teammate.interfaces.ISelectedCity
import com.example.teammate.ui.city.adapter.CityItems
import com.google.android.material.textfield.TextInputEditText

@SuppressLint("ParcelCreator")
class FindGameFragment : Fragment(), ISelectedCity {

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    private var data : CityItems? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title : TextView = requireActivity().findViewById(R.id.toolbar_title)
        title.text = "Найти игру"

        val cats = arrayOf("Футбол", "Волейбол", "Баскетбол", "Хоккей")
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = 3
        binding.numberPicker.displayedValues = cats


        val navigation = Navigation.findNavController(view)

        binding.sport.setOnTouchListener { _, _ ->
            binding.numberPicker.visibility = View.VISIBLE
            binding.button.visibility = View.VISIBLE
            true
        }

        binding.button.setOnClickListener {
            binding.numberPicker.visibility = View.GONE
            binding.button.visibility = View.GONE
            binding.sport.setText(cats[binding.numberPicker.value])

        }

        binding.city.setOnTouchListener { _, _ ->
            if (navigation.currentDestination?.id == R.id.findGameFragment) {
                val bundle = Bundle()
                bundle.putParcelable("fragment", this)
                view.findNavController().navigate(R.id.action_findGame_to_cityFragment, bundle)
            }
            true
        }

        binding.findGame.setOnClickListener {
            if(binding.city.length() > 0 && binding.sport.length() > 0) {
                val bundle = Bundle()
                bundle.putString("sport", binding.sport.text.toString())
                bundle.putString("city", binding.city.text.toString())
                view.findNavController().navigate(R.id.action_findGame_to_foundGame, bundle)
            }
            else
                binding.error.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun selectedCity(data: CityItems, vv: View?) {
        this.data = data
    }

    override fun onResume() {
        super.onResume()
        if(data != null) {
            binding.city.setText(data?.city)
        }
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}