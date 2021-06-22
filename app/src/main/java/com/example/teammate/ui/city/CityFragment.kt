package com.example.teammate.ui.city

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.teammate.R
import com.example.teammate.databinding.FragmentCityBinding
import com.example.teammate.ui.city.adapter.CityAdapter
import com.example.teammate.ui.city.adapter.CityItems
import com.example.teammate.ui.create.CreateGameFragment2
import com.example.teammate.ui.find.FindGameFragment
import com.google.firebase.firestore.auth.User
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception


class CityFragment : Fragment() {

    private var _binding: FragmentCityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Throws(IOException::class)
    private fun readText(context: Context, resId: Int): String {
        val ix: InputStream = context.resources.openRawResource(resId)
        val br = BufferedReader(InputStreamReader(ix))
        val sb = StringBuilder()
        var s: String?
        while (br.readLine().also { s = it } != null) {
            sb.append(s)
            sb.append("\n")
        }
        return sb.toString()
    }

    private val items : ArrayList<CityItems> = ArrayList()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val title : TextView = requireActivity().findViewById(R.id.toolbar_title)
        title.text = "Найти город"

        val jsonText: String = readText(requireContext(), R.raw.russia_regions_list)
        val jsonArray = JSONArray(jsonText)

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            items.add(CityItems(item.getString("region"), item.getString("city")))
        }

        val fragment: Fragment? = requireArguments().getParcelable("fragment")

        val adapter = CityAdapter(layoutInflater, items, view, fragment, ArrayList(items))

        binding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val imm: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = requireActivity().currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.changeText(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.changeText(newText)
                return true
            }
        })




        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
