package com.sportproger.mpt.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.sportproger.domain.module.NotificationTime
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.FragmentTimeBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.viewmodel.TimeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat

class TimeFragment(): Fragment() {
    private val vm by viewModel<TimeViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTimeBinding.inflate(inflater)

        binding.timePicker.setIs24HourView(true)
        Log.d(Conf.MY_LOG, binding.timePicker.is24HourView.toString() + " - is24HourView")
        binding.saveButton.setOnClickListener {
            vm.setNotificationTime(NotificationTime(
                minute = binding.timePicker.minute,
                hour = binding.timePicker.hour,
                dayOfMonth = binding.datePicker.dayOfMonth,
                month = binding.datePicker.month,
                year = binding.datePicker.year
            ))

            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TimeFragment()
    }
}