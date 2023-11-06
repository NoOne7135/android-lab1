package com.example.clockappfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.TimeZone

open class DataModel : ViewModel(){
    val timeZone: MutableLiveData<TimeZone> by lazy {
        MutableLiveData<TimeZone>()
    }
}