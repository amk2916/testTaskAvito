package com.example.testtaskavito.presentation.secondScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SecondScreen : Fragment() {
    private var idItem = UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun parseParam(){
        val args = requireArguments()
        idItem = args.getInt(KEY)
    }
    companion object{
        fun instance(idItem: Int) : SecondScreen{
            return SecondScreen().apply {
                arguments = Bundle().apply {
                    putInt(KEY, idItem)
                }
            }
        }

        private const val KEY = "key"
        private const val UNDEFINED_ID = -1000
    }
}