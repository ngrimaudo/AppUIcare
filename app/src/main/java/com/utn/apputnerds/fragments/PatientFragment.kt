package com.utn.apputnerds.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.apputnerds.R
import com.utn.apputnerds.viewmodels.PatientViewModel

class PatientFragment : Fragment() {

    companion object {
        fun newInstance() = PatientFragment()
    }

    private lateinit var viewModel: PatientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
        // TODO: Use the ViewModel
    }

}