package com.example.testtaskavito.presentation.firstScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.testtaskavito.R

class FilterFragment : Fragment() {
    private var nameCountry: String = ""
    private var ageRating: String = ""
    private var year: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_view,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(view){
            val nameCountryET = findViewById<EditText>(R.id.countryNameET)
            val ageRatingET = findViewById<EditText>(R.id.ageRatingET)
            val yearET = findViewById<EditText>(R.id.yearET)
            val buttonOk = findViewById<Button>(R.id.okButton)

            nameCountryET.setText(nameCountry)
            ageRatingET.setText(ageRating)
            yearET.setText(year)

            buttonOk.setOnClickListener {
                Log.e("filter", nameCountryET.text.toString())
                val resultBundle = Bundle().apply {
                    putString("nameCountry" ,nameCountryET.text.toString())
                    putString("ageRating", ageRatingET.text.toString())
                    putString("year", yearET.text.toString())
                }
                parentFragmentManager.setFragmentResult("requestKey", resultBundle)
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun parseParam(){
        val args = requireArguments()
        with(args){
            val paramsNameCountry  = getString(NAME_COUNTRY)
            val paramsYear  = getString(YEAR)
            val paramsAgeRating  = getString(AGE_RATING)

            if(paramsNameCountry != null) nameCountry = paramsNameCountry
            if(paramsYear != null) year = paramsYear.toString()
            if(paramsAgeRating != null) ageRating = paramsAgeRating.toString()
        }




    }

    companion object{
        fun instance(nameCountry: String? = null, year: Int? = null, ageRating: Int? = null): FilterFragment{
            return FilterFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_COUNTRY, nameCountry)
                    putInt(YEAR, year?:-1)
                    putInt(AGE_RATING, ageRating?:-1)
                }
            }
        }

        private const val NAME_COUNTRY = "nameCountry"
        private const val YEAR = "year"
        private const val AGE_RATING = "ageRating"
    }

}