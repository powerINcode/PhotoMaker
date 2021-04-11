package com.example.ui.livedata

import androidx.lifecycle.LiveData

open class LiveEvent<T>: LiveData<Event<T>>()