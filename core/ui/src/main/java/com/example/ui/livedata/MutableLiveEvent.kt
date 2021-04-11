package com.example.ui.livedata

import android.os.Looper

class MutableLiveEvent<T> : LiveEvent<T>() {
    var event: T? = null
        get() = value?.get()
        set(eventData) {
            field = eventData
            val event = Event(eventData)
            if (Looper.myLooper() == Looper.getMainLooper()) {
                value = event
            } else {
                postValue(event)
            }
        }
}